function checkPas() {
  var pas1 = document.getElementById("singup_password");
  var pas2 = document.getElementById("singup_password2");
  if(pas1.value != '' && pas2.value != '') {
    
    if(pas1.value != pas2.value){
      pas1.style.borderColor = "red";
      pas2.style.borderColor = "red";
       document.getElementById("singup_password2").setCustomValidity("Пароли не совпадают!");
      p = "false";
    }
    else {
      pas1.style.borderColor = "#28921f";
      pas2.style.borderColor = "#28921f";
      
      p = "true";
    }
  }
}

function greenBorder(id){
  document.getElementById(id).style.borderColor = "#28921f";
}

function checkLogin() {
  var xhttp = new XMLHttpRequest();
  var body = 'login=' + encodeURIComponent(document.getElementById("singup_login").value);
  var res;
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      if (String(xhttp.responseText).includes("true")) { //если имя уже есть

        
        document.getElementById("singup_login").style.borderColor = "red";
        document.getElementById("singup_login").setCustomValidity("Логин занят!");
        l = "false";
      } else if(document.getElementById("singup_login").value == '') {
        document.getElementById("singup_login").style.borderColor = "red";
        l = 'false';

      } else {
        document.getElementById("singup_login").style.borderColor = "#28921f";
        l = "true";
      }
    }
  };
  xhttp.open("POST", "http://localhost:8080/website/singup/login",true);
  xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
  xhttp.send(body);
  res = "hello";
  return res;
}

function loadDoc() {
  checkPas();
  checkLogin();
  var s = 'false';
  var n = 'false';
  var pas1 = document.getElementById("singup_password").value;
  var pas2 = document.getElementById("singup_password2").value;
  var name = document.getElementById("singup_name").value;
  var surname = document.getElementById("singup_surname").value;
  if(pas1 == "" && pas2 == ""){
    
    p = 'false';
  } else {
    
  }
  if(name != '')
    n = 'true';
  if(surname != '')
    s = 'true';

  if(l == 'true' && p == 'true' && s == 'true' && n == 'true'){
    var xhttp = new XMLHttpRequest();
    var body = 'login=' + encodeURIComponent(document.getElementById("singup_login").value)
    + '&password=' + encodeURIComponent(document.getElementById("singup_password").value)
    + '&name=' + encodeURIComponent(document.getElementById("singup_name").value)
    + '&surname=' + encodeURIComponent(document.getElementById("singup_surname").value);
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        console.log(xhttp.responseText);
        l = "false";
        p = 'false';
        document.location.href = "http://localhost:8080/website/catalog";
        document.location.href = "http://localhost:8080/website/catalog";
        //---------------------------регистрация пройдена, добавить переход на основной сайт----------
      }
    };
    xhttp.open("POST", "http://localhost:8080/website/singup",true);
    xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhttp.send(body);
  }
}
//--------------------------------------------Вход в систему---------------------------------

function loginSystem() {
  var xhttp = new XMLHttpRequest();
    var body = 'login=' + encodeURIComponent(document.getElementById("login_name").value)
    + '&password=' + encodeURIComponent(document.getElementById("login_password").value)
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        if(xhttp.responseText == ''){
          document.location.href = "http://localhost:8080/website/catalog";
        }
        else {
          console.log(xhttp.responseText);
          document.getElementById("login_error").style.display = "block";
        }
      }
    };
    xhttp.open("POST", "http://localhost:8080/website/login",true);
    xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhttp.send(body);
}


function genBoard() {
  var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        var element = document.getElementsByClassName("productRow");
        element[0].innerHTML = (xhttp.responseText);
        makePrice();
      }
    };
    xhttp.open("POST", "http://localhost:8080/website/catalog",true);
    xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhttp.send();
}

function genPage(name) {
  var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        var element = document.getElementsByClassName("ps");
        element[0].innerHTML = (xhttp.responseText);
        var tmp = document.getElementById("author");
        isMaker(name);
        makePrice();
      }
    };
    xhttp.open("POST", location.href,true);
    xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhttp.send();
}

function isMaker(name) {
  var tmp = document.getElementById("author");
  if(name == tmp.innerHTML) {
    var entry = document.createElement("p")
    entry.innerHTML = "<a class = 'changeButton' onclick = 'changePost()'>изменить</a>";
    document.body.children[0].children[2].append(entry);
  }
}

function makePrice() {

  var changeP = document.getElementsByClassName("productPrice");
  for(var i = 0; i < changeP.length;i++) {
    changeP[i].textContent = "Цена: "+ changeP[i].textContent+" руб.";
  }
  var changeQ = document.getElementsByClassName("productsLeft");
  for(var i = 0; i < changeQ.length;i++) {
    changeQ[i].textContent = "В наличии: "+ changeQ[i].textContent;
  }
  var changePur = document.getElementsByClassName("purchaseProduct");
  for(var i = 0; i < changePur.length;i++) {
    changePur[i].textContent = "В корзину";
  }
}



/*------------------------------------------------изменение продукта----------------------------*/
function changePost() {
  var elem = document.getElementsByClassName("pageLabel");
  prevName =elem[0].textContent;
  document.body.children[0].children[2].children[8].remove();  
  var change = document.getElementsByClassName("changeButton");
  var entry = document.createElement('p');
  entry.innerHTML = '<a onclick = "saveChanges()">Сохранить</a>';
  document.body.children[0].children[2].append(entry); 
  var entry2 = document.createElement('p');
  entry2.innerHTML = '<a onclick = "cancelChanges()">Отменить</append>';
  document.body.children[0].children[2].append(entry2);
 // change[0].innerHTML = saveChanges;
  var changeDesk = document.getElementsByClassName("productDecription")[0];
  changeDesk.innerHTML = '<textarea name="decript" cols="40" rows="6" input id = "changePageDecr" onchange = "changePageDecr()">' +changeDesk.textContent + '</textarea>';  

  var changeQuantity = document.getElementsByClassName("productsLeft")[0];
  changeQuantity.innerHTML = '<input pattern="^[ 0-9]+$" id = "changePageQuantity" type = "text" value = "' + changeQuantity.textContent + '"onchange = "changePageQuantity()">';

  var changePrice = document.getElementsByClassName("productPrice")[0];
  changePrice.innerHTML = '<input pattern="^[ 0-9]+$" id = "changePagePrice" type = "text" value = ' + changePrice.textContent + 'onchange = "changePagePrice()">';

  var prevName = elem[0].innerHTML;
/*  var str = '<input id = "changePageLabel" type = "text" value ="' +elem[0].innerHTML+'" onchange = "changePageLabel()">';
  elem[0].innerHTML = str ;*/
}

function cancelChanges() {
  genPage();
  document.body.children[0].children[2].children[8].remove(); 
  document.body.children[0].children[2].children[8].remove(); 
  var entry = document.createElement('p');
  entry.innerHTML = "<a class = 'changeButton' onclick = 'changePost()'>изменить</a>";
  document.body.children[0].children[2].append(entry);

    
}

function saveChanges(){
  var xhttp = new XMLHttpRequest();
  var count = 0;
  var body = 'prevName='+prevName;


  if(price == 'true')
    body+= '&price='+document.getElementById("changePagePrice").value;
  else{
    body+='&price=null';
    count++
  }

  if(decript== 'true')
    body+= '&decript='+document.getElementById("changePageDecr").value;
  else{
    body+='&decript=null';
    count++
  }

  if(quiantity== 'true')
    body+= '&quiantity='+document.getElementById("changePageQuantity").value;
  else{
    body+='&quiantity=null';
    count++;
  }
  if(count == 3)
    return;
   label = "false"
   price = "false"
   quiantity = "false"
   decript = "false"
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        console.log(xhttp.responseText);
        console.log("WOWOWO");
      }
    };
    xhttp.open("POST", "http://localhost:8080/website/catalog/change",true);
    xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhttp.send(body);

}


/*----------------------------------------трекаю изменения------------------------*/

function changePageLabel() {
  label = "true";
}

function changePagePrice() {
  price = "true";
}

function changePageDecr() {
  decript = "true";
}

function changePageQuantity() {
  quiantity = "true";
}


/*----------------------------------------корзина------------------------------------*/

function genPurchase(login) {
  var xhttp = new XMLHttpRequest();
    body = "gen=true&login="+login;
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        var div = document.getElementById('main_center');
        console.log(div);
        div.innerHTML = xhttp.responseText;
        console.log(getTotalPrice());
      }
    };
    xhttp.open("POST", "http://localhost:8080/website/purchase",true);
    xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhttp.send(body);
}

function addToPurchase(id,login, add) {
  var xhttp = new XMLHttpRequest();
  var body;
  if(!add) {
    body = 'name='+id+'&quiantity=-1&login='+login;;
  }
  else {
    body = 'name='+id+'&quiantity=1&login='+login;
  }
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        if( document.getElementsByClassName("purchase") > 0) {

        }
        var tmp = document.getElementsByClassName("purchase")[0];
        tmp.textContent = "корзина "+ parseInt(xhttp.responseText) ;
      }
    };
    xhttp.open("POST", "http://localhost:8080/website/purchase",true);
    xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhttp.send(body);
}

function getTotalPrice() {
   var tmp1 =  document.getElementsByClassName("purchaseProductName");
   var tmp2 = document.getElementsByClassName("purchaseProductPrice");
   var tmp3 = document.getElementsByClassName("purchaseProductQuintity");
   var tmp4 = document.getElementsByClassName("purchaseTotalPrice");
   var totalPrice = 0;
   for(var i = 0; i< tmp1.length; i++) {
    console.log(tmp2[i].textContent + " " + tmp3[i].value);
      totalPrice +=  tmp2[i].textContent*tmp3[i].value;
      tmp4[i].textContent = " итог: " + tmp2[i].textContent*tmp3[i].value;
      tmp1[i].textContent = "Название товара: " + tmp1[i].textContent;
      tmp2[i].textContent = "цена: " + tmp2[i].textContent;
      tmp3[i].textContent = "количество: " + tmp3[i].textContent;
   }
   var tmp5 = document.getElementsByClassName("purchaseBuyButton");
   var tmp6 = document.getElementsByClassName("purchaseTotal");
   for (var i = 0; i < tmp5.length; i++) {
     tmp5[i].textContent = 'купить';
     tmp6[i].textContent = totalPrice + " руб";
   }
}


/* -----------------------------покупка товара--------------------------------*/

function buyProducts(login) {
  var xhttp = new XMLHttpRequest();
  console.log(login);
  var body ='login='+login;
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        genPurchase(login);
        //console.log(xhttp.responseText);
      }
    };
    xhttp.open("POST", "http://localhost:8080/website/buyProducts",true);
    xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhttp.send(body);
}


function changeQuiantity(login) {
  console.log(name);
}


function addProd(num, name, login) {
  var body = 'name=' + name; 
  var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        var n = parseInt(document.getElementsByClassName("purchaseProductQuintity")[num].value);
        n += parseInt(1);
        console.log(xhttp.responseText);  
        if(n <= xhttp.responseText) {
          document.getElementsByClassName("purchaseProductQuintity")[num].value = n;
          addToPurchase(name,login, true);
          reloadPrice(name);
        }
      }
    };
    xhttp.open("POST", "http://localhost:8080/website/getQuintity",true);
    xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhttp.send(body);

}

function subtractProd(num, name, login) {
  //странно
  if(document.getElementsByClassName("purchaseProductQuintity")[num].value - 1 >= 0) {
    document.getElementsByClassName("purchaseProductQuintity")[num].value -= 1;
    addToPurchase(name, login, false);
    reloadPrice(name);
  }
}

function reloadPrice(name) {
    var tmp1 = document.getElementsByClassName("purchaseProductName");
    for(var i = 0; i < tmp1.length; i++) {
      if(tmp1.textContent == name)
        var tmp2 = document.getElementsByClassName("purchaseProductPrice")[i];
        var tmp3 = document.getElementsByClassName("purchaseProductQuintity")[i];
    }
} 

function search(){
  var tmp = document.getElementsByClassName("findSearch")[0].value;
  var body = "arg="+tmp; 
  var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        var element = document.getElementsByClassName("productRow");
        element[0].innerHTML = (xhttp.responseText);
        makePrice();
      }
    };
    xhttp.open("POST", "http://localhost:8080/website/search",true);
    xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhttp.send(body);
}



var prevName;

var label = "false"
var price = "false"
var quiantity = "false"
var decript = "false"

var l = "false";
var p = "false";


