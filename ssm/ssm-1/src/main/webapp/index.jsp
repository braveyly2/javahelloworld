<html>
<body>
<h2>Hello World!</h2>
<a href="message/go">i am going to redirect</a>
<div>
    username:<input type="text" id="userName" name="userName"/>
    <input type="button" value="find user" onClick="Search()"/>
    <form id="report" action="/message/report2">
        <input id="begin" type="text" name="begin"/>
        <input id="end" type="text" name="end"/>
        <input id="reportCommit" type="submit" name="submmit"/>
    </form>
</div>
</body>
</html>
<script>
    function Search(){
        window.open("/message/detail/data="+document.getElementById("userName").value);
    }
</script>