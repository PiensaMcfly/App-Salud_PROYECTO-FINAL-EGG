var theme1 = document.querySelector('.theme1');
var body = document.querySelector('body');
var darkModeToggle = document.getElementById('darkModeToggle');

theme1.onclick = function () {
    theme1.classList.toggle('active');
    body.classList.toggle('active');
    // Aquí puedes agregar lógica adicional si es necesario
}

darkModeToggle.addEventListener('change', function () {
    if (darkModeToggle.checked) {
        theme1.classList.add('active');
        body.classList.add('active');
    } else {
        theme1.classList.remove('active');
        body.classList.remove('active');
    }
});