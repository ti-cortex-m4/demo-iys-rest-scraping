select
CONCAT(IFNULL(skill_type,''), '[:]', skill_id, IFNULL(ancestor_ids,''), '[:]', is_template, '[:]', IFNULL(skill_json,'')) AS id,
CONCAT(skill_name, IF(LENGTH(IFNULL(synonyms, ''))>0, CONCAT('[:]',synonyms), ''), IF(LENGTH(IFNULL(ancestor_names, ''))>0, CONCAT('<=',ancestor_names), '')) AS text
from `skill_engine_v2v1`
where `is_searchable` = 1
and `is_template` = 0
and `is_verified` = 1
and (`search_term` LIKE %)
order by `skill_rank` desc, LENGTH(search_term) asc