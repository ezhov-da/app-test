package ru.ezhov.help;

import com.google.common.base.Strings;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.*;

/**
 * Created by ezhov_da on 03.05.2018.
 */
public class JavaClasses {
	private static final Logger LOG = Logger.getLogger(JavaClasses.class.getName());

	private static Pattern patternPackage = Pattern.compile("(?<=package\\s)(\\w+\\.?)+");
	private static Pattern patternImport = Pattern.compile("(?<=import\\s)(\\w+\\.?)+");
	private static Pattern patternClass = Pattern.compile("(?<=class\\s)(\\w+\\.?)+");
	//	private static Pattern patternWord = Pattern.compile("\\w+");
	private static Pattern patternWord = Pattern.compile("(?<=\\t)\\w+|(?<=\\()\\w+|(?<= )\\w+");

	private static Map<String, Set<JavaFile>> mapWords = new HashMap<>();

	public static void main(String[] args) {
		String source = "E:/MDM/branches/v00mdmx-dev/MDM/src";
		List<JavaFile> javaFiles = show(new File(source), new ArrayList<>());
		int i = 10;

		for (JavaFile javaFile : javaFiles) {
			if (mapWords.containsKey(javaFile.name())) {
				System.out.println(Strings.repeat("=", 80));
				System.out.printf(
					"Класс %s [%s] используется:\n",
					javaFile.id(),
					javaFile.file().getAbsolutePath()
				);
				//бинго, найдено название нашего класса
				//но может произойти так, что название совпадает с классом из другого пакета, нужно это проверить
				Set<JavaFile> fileSet = mapWords.get(javaFile.name());
				//сначала проверяем импорты
				for (JavaFile file : fileSet) {
					//исключаем себя
					if (javaFile.id().equals(file.id())) {
						continue;
					}

					boolean contains = false;
					for (String imports : file.imports()) {
						if (imports.contains(javaFile.packageName())) {
							contains = true;
							break;
						}
					}

					//затем проверяем расположение в одном пакете
					if (javaFile.packageName().equals(file.packageName()) || contains) {
						System.out.printf("\t %s [%s]\n", file.id(), file.file().getAbsolutePath());
					}
				}
			} else {
				System.out.println(Strings.repeat("_", 80));
				System.out.printf(
					"Класс %s [%s] НЕ используется в:\n",
					javaFile.id(), javaFile.file().getAbsolutePath()
				);
			}
		}
	}

	public static List<JavaFile> show(File file, List<JavaFile> javaFiles) {
		File[] files = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".java") || pathname.isDirectory();
			}
		});

		for (File f : files) {
			if (f.isDirectory()) {
				show(f, javaFiles);
			} else {
				JavaFile javaFile = new JavaFile(f);
				System.out.println(Strings.repeat("=", 80));
				System.out.println("file: " + f.getAbsolutePath());
				try (BufferedReader bufferedReader = new BufferedReader(new FileReader(f))) {
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						if (line.contains("package")) {
							Matcher matcher = patternPackage.matcher(line);
							if (matcher.find()) {
								String packages = matcher.group();
								javaFile.packageName(packages);
								System.out.println("package: " + packages);
							} else {
								System.err.println("В классе " + f.getAbsolutePath() + " не указан пакет");
							}
						}
						if (line.contains("import")) {
							Matcher matcher = patternImport.matcher(line);
							while (matcher.find()) {
								String imports = matcher.group();
								javaFile.imports(imports);
								System.out.println("import: " + imports);
							}
						}
						if (line.contains("class")) {
							Matcher matcher = patternClass.matcher(line);
							while (matcher.find()) {
								String className = matcher.group();
								javaFile.declareClass(className);
								System.out.println("declare class: " + className);
							}
						}

						Matcher matcher = patternWord.matcher(line);
						while (matcher.find()) {
							String word = matcher.group();
							javaFile.word(word);

							Set<JavaFile> idClass;
							if (mapWords.containsKey(word)) {
								idClass = mapWords.get(word);
							} else {
								idClass = new HashSet<>();
								mapWords.put(word, idClass);
							}
							idClass.add(javaFile);
						}
					}

					javaFiles.add(javaFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return javaFiles;
	}
}

class JavaFile {
	private File file;
	private String packageName;
	private Set<String> imports;
	private Set<String> declareClassess;
	private Set<String> words;

	public JavaFile(File file, String packageName) {
		this.file = file;
		this.packageName = packageName;
		this.imports = new HashSet<>();
		this.declareClassess = new HashSet<>();
		this.words = new HashSet<>();
	}

	public JavaFile(File file) {
		this(file, "unknown");
	}

	public void imports(String importName) {
		this.imports.add(importName);
	}

	public Set<String> imports() {
		return this.imports;
	}

	public void declareClass(String importName) {
		this.declareClassess.add(importName);
	}

	public void word(String word) {
		this.words.add(word);
	}

	public void packageName(String packageName) {
		this.packageName = packageName;
	}

	public String packageName() {
		return this.packageName;
	}

	public String name() {
		return file.getName().replaceAll("\\.java", "");
	}

	public String id() {
		return packageName + "." + name();
	}

	public File file() {
		return file;
	}
}
