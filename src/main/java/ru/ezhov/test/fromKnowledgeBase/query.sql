SELECT * FROM t_e_note t0
INNER JOIN  t_e_note_detail t1 ON t0.id = t1.id
where cast(ishide as INTEGER) = 0
ORDER BY NAME