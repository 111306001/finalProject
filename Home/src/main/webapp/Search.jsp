<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GoogleSearch</title>
    <script type="text/javascript">
        function getName() {
            var resultText = "";
            var checkboxes = document.querySelectorAll('input[type=checkbox]:checked');

            checkboxes.forEach(function(checkbox) {
                resultText += " " + checkbox.value;
            });

            var keywordInput = document.getElementById('keyword');
            keywordInput.value += resultText.trim();
        }

    </script>
</head>
<body>

<form action='${requestUri}' method='get' >
    <div>
        <input type='text' class="border-style" id="keyword" name='keyword' placeholder='請輸入關鍵字' onfocus="placeholder= '' " onblur="placeholder='請輸入關鍵字'" />
        <input type='image' src="images/loupe-2.png"/>
        <button type="button" name="add" onclick="getName()">Add</button>
    </div>
    <div>
        <input type='checkbox' name='ticket' value="ticket" >Ticket
        <input type='checkbox' name='hotel' value="hotel" >Hotel
        <input type='checkbox' name='attractions' value="attractions" >Attractions
        <input type='checkbox' name='tripPlan' value="tripPlan" >Trip plan
        <input type='checkbox' name='food' value="food" >Food
    </div>

    <p>Hashtag:
        <input type='checkbox' name='recommendation' value="recommendation" >Recommendation
        <input type='checkbox' name='feature' value="feature" >Feature
        <input type='checkbox' name='mostPopular' value="mostPopular">Most popular
    </p>
<a href ='http://localhost:8080/Final_Project/TestProject'></a>
</form>
</body>
</html>
