SELECT
  t0.name,
  t1.link,
  t1.text
FROM t_e_note t0
  INNER JOIN t_e_note_detail t1 ON t0.id = t1.id
WHERE
  cast(ishide AS INTEGER) = 0
  AND
    LEFT(t0.name, 1) = '-'
ORDER BY NAME