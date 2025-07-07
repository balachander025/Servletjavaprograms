<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
    <title>Batch Insert Users</title>
</head>
<body>
    <h2>Insert Users</h2>
    <form action="batchprocessing" method="post">
        <div id="userInputs">
            <div>
                Name: <input type="text" name="name" required>
                Email: <input type="email" name="email" required>
            </div>
        </div>
        <button type="button" onclick="addUserInput()">Add Another User</button><br><br>
        <input type="submit" value="Submit">
    </form>

    <script>
        function addUserInput() {
            var div = document.createElement('div');
            div.innerHTML = 'Name: <input type="text" name="name" required> ' +
                            'Email: <input type="email" name="email" required>';
            document.getElementById('userInputs').appendChild(div);
        }
    </script>
</body>
</html>
