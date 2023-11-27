/*
list.users.by.key.and.client.id
*/
SELECT
	u.* 
FROM
	t_user u
RIGHT JOIN (
	SELECT
		* 
	FROM
		t_client_user 
    WHERE
	    au_client_id = @clientId
) cu
ON u.u_name = cu.au_user_name
<%if(hasKey){%>
    WHERE
        u.u_name LIKE @key
        OR u.u_mobile LIKE @key
<%}%>	
/*
count.users.by.key.and.client.id
*/
SELECT
	count(u.id)
FROM
	t_user u
	RIGHT JOIN (
	SELECT
		* 
	FROM
		t_client_user 
WHERE
	au_client_id = @clientId ) cu ON u.u_name = cu.au_user_name
<%if(hasKey){%>
WHERE
			u.u_name LIKE @key
			OR u.u_mobile LIKE @key
<%}%>	
/*
users.not.in.current.client
*/
SELECT
	* 
FROM
	t_user 
WHERE
	u_name NOT IN (
	SELECT
		au_user_name 
	FROM
		t_client_user 
WHERE
	au_client_id = @clientId)
/*
users.with.department
*/
SELECT
	tu.u_full_name,
	tu.u_mobile,
	tu.u_email,
	tu.u_phone,
	GROUP_CONCAT(td.d_full_name)  AS department
FROM
	t_user tu
LEFT JOIN t_department_member tdm ON
	tdm.dm_user_name = tu.u_name
LEFT JOIN t_department td ON
	td.d_department_id = tdm.dm_department_id
$condition
GROUP BY tu.u_name
<% if (searchKey != "") { %>
HAVING $searchField LIKE @searchKey
<% } %>
/*
count.users.with.department
*/
SELECT COUNT(ud.u_name) FROM (
    SELECT
        tu.u_name,
        tu.u_full_name,
        tu.u_mobile,
        tu.u_email,
        tu.u_phone,
        GROUP_CONCAT(td.d_full_name)  AS department
    FROM
        t_user tu
    LEFT JOIN t_department_member tdm ON
        tdm.dm_user_name = tu.u_name
    LEFT JOIN t_department td ON
        td.d_department_id = tdm.dm_department_id
    $condition
    GROUP BY tu.u_name
    <% if (searchKey != "") { %>
    HAVING $searchField LIKE @searchKey
    <% } %>
) ud

/*
users.in.current.client
*/
SELECT
	*
FROM
	t_user
WHERE
	u_name  IN (
	SELECT
		au_user_name
	FROM
		t_client_user
WHERE
	au_client_id = @clientId)
