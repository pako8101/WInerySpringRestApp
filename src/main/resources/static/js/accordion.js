function toggle() {
    const content = document.querySelector("#extra");
    //const button = document.querySelector("span.button");
    let button = document.getElementsByClassName('button')[0];
    content.style.display = content.style.display === "block" ? 'none' : "block";
    button.textContent =  button.textContent === "More" ? 'Less' : "More";
    /* if (content.style.display !== "block") {
       content.style.display = "block";

       button.textContent = "Less";
     }else{
       content.style.display = "none";

       button.textContent = "More";
     }*/


}