Attribute VB_Name = "Generator"
Sub generate()
    Dim r As Long: r = 1
        Cells(r, 1) = "Ñעמבוצ 1"
        Cells(r, 2) = "Ñעמבוצ 2"
        Cells(r, 3) = "Ñעמבוצ 3"
        Cells(r, 4) = "Ñעמבוצ 4"
        Cells(r, 5) = "Ñעמבוצ 5"
    r = r + 1
    Do While (r < 500001)
        Cells(r, 1) = "אבגדהווזחטיךכאלםמןנסעףפץצקש"
        Cells(r, 2) = "אבגדהווזחטיךכאלםמןנסעףפץצקש"
        Cells(r, 3) = "אבגדהווזחטיךכאלםמןנסעףפץצקש"
        Cells(r, 4) = "אבגדהווזחטיךכאלםמןנסעףפץצקש"
        Cells(r, 5) = "אבגדהווזחטיךכאלםמןנסעףפץצקש"
        r = r + 1
    Loop
End Sub
