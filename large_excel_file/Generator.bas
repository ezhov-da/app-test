Attribute VB_Name = "Generator"
Sub generate()
    Dim r As Long: r = 1
        Cells(r, 1) = "������ 1"
        Cells(r, 2) = "������ 2"
        Cells(r, 3) = "������ 3"
        Cells(r, 4) = "������ 4"
        Cells(r, 5) = "������ 5"
    r = r + 1
    Do While (r < 500001)
        Cells(r, 1) = "���������������������������"
        Cells(r, 2) = "���������������������������"
        Cells(r, 3) = "���������������������������"
        Cells(r, 4) = "���������������������������"
        Cells(r, 5) = "���������������������������"
        r = r + 1
    Loop
End Sub
