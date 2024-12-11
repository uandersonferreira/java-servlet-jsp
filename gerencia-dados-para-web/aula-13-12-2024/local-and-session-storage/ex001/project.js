const nameForm = document.querySelector("#name-form");
const welcomeContainer = document.querySelector("#welcome");
const logoutButton = document.querySelector("#logout");


function checkUser(){
    const userName = localStorage.getItem("name");
    if(userName){
        nameForm.style.display = "none"; // Esconde o formulário

        welcomeContainer.style.display = "block"; // Exibe a mensagem de boas-vindas

        const userNameElement  = document.querySelector("#username");

        userNameElement.textContent = userName;

    }else{
        nameForm.style.display = "block"; // Exibor o formulário

        welcomeContainer.style.display = "none"; // Esconder a mensagem de boas-vindas

    }

};

nameForm.addEventListener("submit", (event) => {
  event.preventDefault(); // Evitar que o formulário seja enviado e recarregue a página
  
  const nameInput = document.querySelector("#name");

  localStorage.setItem("name", nameInput.value);

  nameInput.value = "";

  checkUser();

});

logoutButton.addEventListener("click", () => {

  localStorage.removeItem("name");

  checkUser();

});


// Será executado assim que a página carregar
checkUser();
