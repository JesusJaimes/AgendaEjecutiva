
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


