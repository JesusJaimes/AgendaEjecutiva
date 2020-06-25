
function validarContraseña(){
    var password, password2;
    password = document.getElementById('password');
    password2 = document.getElementById('password-repetida');
    password.onchange = password2.onkeyup = passwordMatch;

    function passwordMatch() {
        if(password.value !== password2.value){
            password2.setCustomValidity('Las contraseñas no coinciden');
        }else{
            password2.setCustomValidity('');
        } 
    }
}

function openSection(evt, sectionName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("lista-citas");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    
    document.getElementById(sectionName).style.display = "flex";
    evt.currentTarget.className += " active";
}

