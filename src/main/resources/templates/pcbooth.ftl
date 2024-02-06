<html>
<head>
    <title>Anthony's design</title>
</head>
<body>

<h3>Please click below button send tasks to all laptops.</h3>
<#--<#list TaskList.items as item>
    <button>${item}</button>
</#list>-->
<a href="http://172.18.1.232:50000/noTask"> Set all laptops' task to noTask.</a><br/><br/>
<a href="http://172.18.1.232:50000/shutdown"> Set all laptops' task to shutdown.</a><br/><br/>
<a href="http://172.18.1.232:50000/reboot"> Set all laptops' task to reboot.</a><br/><br/>


<h2>Now all laptops' task are : </h2>
<h2 style="color: red">${TaskList.task}</h2>
<h3>Tasks send out time is :${TaskList.localDateTimeSubstring} </h3>


</body>
</html>
