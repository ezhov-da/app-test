package ru.ezhov.test.jira

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File

const val GITLAB_HOST_PROP = "gitLabHost"
const val GITLAB_API_TOKEN_PROP = "gitLabApiToken"
const val GITLAB_PROJECT_ID_PROP = "gitLabProjectId"
const val JIRA_HOST_PROP = "jiraHost"
const val JIRA_USERNAME_PROP = "jiraUsername"
const val JIRA_PASSWORD_PROP = "jiraPassword"

fun main() {
    val gitLab = gitLab()
    val jira = jira()

    val branches = branches(gitLab)
    val mrs = mrs(gitLab)
    val analyse = analyse(branches, mrs, jira)
    createReport(analyse, File("../branch-report.xlsx"))
}

private fun gitLab() = GitLab(
        host = System.getProperty(GITLAB_HOST_PROP)!!,
        apiToken = System.getProperty(GITLAB_API_TOKEN_PROP)!!,
        projectId = System.getProperty(GITLAB_PROJECT_ID_PROP)!!
)

private fun jira() = Jira(
        host = System.getProperty(GITLAB_HOST_PROP)!!,
        username = System.getProperty(JIRA_USERNAME_PROP)!!,
        password = System.getProperty(JIRA_PASSWORD_PROP)!!
)

private fun branches(gitLab: GitLab): List<Branch> {
    val listAll = mutableListOf<Branch>()

    val client = OkHttpClient()

    var pageCount = 1

    print("get branch page ")
    while (true) {
        print("$pageCount ")

        val requestMr = Request.Builder()
                .url("http://${gitLab.host}/api/v4/projects/${gitLab.projectId}/repository/branches?page=$pageCount")
                .header("Content-Type", "application/json")
                .header("PRIVATE-TOKEN", gitLab.apiToken)
                .get()
                .build()
        val responseMrs = client.newCall(requestMr).execute()
        val responseMrsText = responseMrs.body().string()

//        println(responseMrsText)

        val om = ObjectMapper()
        val mrNodes = om.readTree(responseMrsText).asIterable()

        val listPage = mutableListOf<Branch>()

        mrNodes.forEach {
            val name = it.get("name").asText()

            listPage.add(Branch.create(name = name))
        }

        if (listPage.isEmpty()) {
            println("")
            return listAll
        } else {
            listAll.addAll(listPage)
        }

        pageCount++
    }
}

private fun task(taskName: String, jira: Jira): Task {
    println("get task by name $taskName")

    val client = OkHttpClient()

    val requestBodyLogin = RequestBody
            .create(
                    MediaType.parse("application/json"),
                    "{\"username\": \"${jira.username}\", \"password\": \"${jira.password}\"}"
            )

    val requestLogin = Request.Builder()
            .url("https://jira.app.local/rest/auth/1/session")
            .header("Content-Type", "application/json")
            .post(requestBodyLogin)
            .build()

    val responseLogin = client.newCall(requestLogin).execute()

//    responseLogin.headers().names().forEach { println("$it -> ${responseLogin.header(it)}") }
    val cookies = responseLogin.header("Set-Cookie")


    val responseLoginText = responseLogin.body().string()

//    println(responseLoginText)

    val om = ObjectMapper()
    val jsonNode = om.readTree(responseLoginText)

    val seesionNode = jsonNode.get("session")
    val name = seesionNode.get("name").asText()
    val value = seesionNode.get("value").asText()

//    println("$name=$value")


    val requestTaskInfo = Request.Builder()
            .url("https://${jira.host}/rest/api/2/issue/$taskName")
            .header("Content-Type", "application/json")
            .header("Cookie", "$name=$value; $cookies")
            .get()
            .build()

    val responseTask = client.newCall(requestTaskInfo).execute()
    val responseTaskText = responseTask.body().string()

//    println(responseTaskText)

    val jsonNodeTask = om.readTree(responseTaskText)

    val fieldsNode = jsonNodeTask.get("fields")
    val status = fieldsNode.get("status").get("name").asText()
    val username = fieldsNode.get("assignee").get("displayName").asText()
    val statusDate = fieldsNode.get("resolutiondate").asText()

//    println("$taskName $username $result")

    return Task(status, username, statusDate)
}

private fun mrs(gitLab: GitLab): List<Mr> {
    val listMrAll = mutableListOf<Mr>()

    val client = OkHttpClient()

    var pageCount = 1

    print("get mr page ")
    while (true) {
        print("$pageCount ")

        val requestMr = Request.Builder()
                .url("http://${gitLab.host}/api/v4/projects/${gitLab.projectId}/merge_requests?state=all&scope=all&page=$pageCount")
                .header("Content-Type", "application/json")
                .header("PRIVATE-TOKEN", gitLab.apiToken)
                .get()
                .build()
        val responseMrs = client.newCall(requestMr).execute()
        val responseMrsText = responseMrs.body().string()

//        println(responseMrsText)

        val om = ObjectMapper()
        val mrNodes = om.readTree(responseMrsText).asIterable()

        val listMrPage = mutableListOf<Mr>()

        mrNodes.forEach {
            val title = it.get("title").asText()
            val state = it.get("state").asText()
            val created = it.get("created_at").asText()
            val author = it.get("author").get("name").asText()

            listMrPage.add(
                    Mr(
                            title = title,
                            state = state,
                            created = created,
                            author = author
                    )
            )

        }

        if (listMrPage.isEmpty()) {
            println("")
            return listMrAll
        } else {
            listMrAll.addAll(listMrPage)
        }

        pageCount++
    }
}

private fun analyse(branches: List<Branch>, mrs: List<Mr>, jira: Jira): List<Report> {
    return branches
            .map {
                val branch = it
                var task: Task? = null
                var mrsTask: List<Mr>? = null
                if (it.taskName != null) {
                    task = task(it.taskName, jira)
                    mrsTask = mrs.filter { mr -> mr.title.contains(other = it.taskName, ignoreCase = true) }
                }
                Report(
                        branch = branch,
                        task = task,
                        mrs = mrsTask
                )

            }
}

fun createReport(analyse: List<Report>, reportFile: File) {
    val workbook = XSSFWorkbook()
    val sheet = workbook.createSheet()
    var rowCount = 0

    var row = sheet.createRow(rowCount)
    row.createCell(0).setCellValue("Имя ветки")
    row.createCell(1).setCellValue("Имя задачи из ветки")
    row.createCell(2).setCellValue("Статус задачи из Jira")
    row.createCell(3).setCellValue("Исполнитель по задаче")
    row.createCell(4).setCellValue("Дата статуса по задаче")
    row.createCell(5).setCellValue("Заголовок MR по задаче")
    row.createCell(6).setCellValue("Статус MR")
    row.createCell(7).setCellValue("Создан MR")
    row.createCell(8).setCellValue("Автор MR")

    rowCount++

    analyse.forEach { report ->
        val branchName = report.branch.name
        val branchTaskName = report.branch.taskName.orEmpty()
        val jiraStatus = report.task?.status.orEmpty()
        val jiraEmployee = report.task?.username.orEmpty()
        val jiraStatusDate = report.task?.statusDate.orEmpty()

        if (report.mrs != null) {
            report.mrs.forEach { mr ->
                row = sheet.createRow(rowCount)
                row.createCell(0).setCellValue(branchName)
                row.createCell(1).setCellValue(branchTaskName)
                row.createCell(2).setCellValue(jiraStatus)
                row.createCell(3).setCellValue(jiraEmployee)
                row.createCell(4).setCellValue(jiraStatusDate)
                row.createCell(5).setCellValue(mr.title)
                row.createCell(6).setCellValue(mr.state)
                row.createCell(7).setCellValue(mr.created)
                row.createCell(8).setCellValue(mr.author)
                rowCount++
            }
        } else {
            row = sheet.createRow(rowCount)
            row.createCell(0).setCellValue(branchName)
            row.createCell(1).setCellValue(branchTaskName)
            row.createCell(2).setCellValue(jiraStatus)
            row.createCell(3).setCellValue(jiraEmployee)
            row.createCell(4).setCellValue(jiraStatusDate)
            rowCount++
        }
    }

    workbook.write(reportFile.outputStream())
    workbook.close()
}

class Branch private constructor(
        val name: String,
        val taskName: String?
) {
    companion object {
        fun create(name: String): Branch {
            val regex = Regex("SMSTORE-\\d+")
            val taskName = regex.find(name)?.groupValues?.first()

            return Branch(
                    name = name,
                    taskName = taskName
            )

        }
    }
}

data class GitLab(
        val host: String,
        val apiToken: String,
        val projectId: String
)

data class Jira(
        val host: String,
        val username: String,
        val password: String
)

data class Task(
        val status: String,
        val username: String,
        val statusDate: String?
)

data class Mr(
        val title: String,
        val state: String,
        val created: String,
        val author: String
)

data class Report(
        val branch: Branch,
        val task: Task?,
        val mrs: List<Mr>?
)