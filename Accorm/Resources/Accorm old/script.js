var r = document.querySelector(':root');

function home() {
  window.location="https://accorm.acc-web.repl.co/";
}
function contribute() {
  window.location="https://accorm.acc-web.repl.co/contribute/";
}
function openThemes() {
  document.querySelector(".colors").style.display="flex";
}
function closeThemes() {
  document.querySelector(".colors").style.display="none";
}
function openNav() {
  $("#sidebar").css("left", "0px");
}
function closeNav() {
  $("#sidebar").css("left", "-100%");
}

var themes = {
  "def": [
    "rgba(15,15,22,1)",
    "rgba(30, 30, 44, 1)",
    "#acacf9",
    "#8787c6",
    "#4b4b6e",
    "url(def-back.png)",
    "rgba(172, 172, 249, 0.15)",
    "rgba(172, 172, 249, 0.1)",
    "rgba(46, 55, 72, 1)"
  ],
  "ored": [
    "rgba(45, 21, 21, 1)",
    "rgba(90, 42, 42, 1)",
    "rgba(255, 150, 150, 1)",
    "rgba(213, 100, 100, 1)",
    "rgba(159, 75, 75, 1)",
    "url(ored-back.png)",
    "rgba(255, 150, 150, 0.15)",
    "rgba(255, 150, 150, 0.1)",
    "rgba(75, 35, 35, 1)"
  ],
  "gween": [
    "rgba(0, 36, 25, 1)",
    "rgba(0, 72, 50, 1)",
    "rgba(0, 255, 180, 1)",
    "rgba(0, 201, 142, 1)",
    "rgba(0, 142, 100, 1)",
    "url(gween-back.png)",
    "rgba(0, 255, 180, 0.15)",
    "rgba(0, 255, 180, 0.1)",
    "rgba(0, 60.5, 42, 1)"
  ],
  "glue": [
    "rgba(0, 25, 36, 1)",
    "rgba(0, 50, 72, 1)",
    "rgba(0, 180, 255, 1)",
    "rgba(0, 142, 201, 1)",
    "rgba(0, 100, 142, 1)",
    "url(glue-back.png)",
    "rgba(0, 180, 255, 0.15)",
    "rgba(0, 180, 255, 0.1)",
    "rgba(0, 42, 60.5, 1)"
  ],
  "teel": [
    "rgba(0, 30, 30, 1)",
    "rgba(0, 60, 60, 1)",
    "rgba(0, 200, 200, 1)",
    "rgba(0, 150, 150, 1)",
    "rgba(0, 100, 100, 1)",
    "url(teel-back.png)",
    "rgba(0, 200, 200, 0.15)",
    "rgba(0, 200, 200, 0.1)",
    "rgba(0, 50, 50, 1)"
  ]
}

/** 
0: back
1: navBar
2: color1
3: color2
4: color3
5: background image
6: transparent 1
7: transparent 2
8: sidebar background
*/




function themeColor(color) {
  var themes = document.querySelector(".t-c");
  var tcolor = "#"+color+"-t";
  
  $(".t-c").css("display", "none");
  
  $(tcolor).css("display", "block");
  localStorage.setItem("theme", color);
  window.location.reload();
}

window.onload = () => {
  if(localStorage.getItem("theme")) {
    var savedTheme = "#"+localStorage.getItem("theme")+"-t";
    $(".t-c").css("display", "none");
    $(savedTheme).css("display", "block");

    var themeName = localStorage.getItem("theme");
    
    r.style.setProperty('--back',   themes[themeName][0]);
    r.style.setProperty('--navBar', themes[themeName][1]);
    r.style.setProperty('--color1', themes[themeName][2]);
    r.style.setProperty('--color2', themes[themeName][3]);
    r.style.setProperty('--color3', themes[themeName][4]);
    r.style.setProperty('--bg-img', themes[themeName][5]);
    r.style.setProperty('--trans1', themes[themeName][6]);
    r.style.setProperty('--trans2', themes[themeName][7]);
    r.style.setProperty('--s-back', themes[themeName][8]);
  }
  else {
    r.style.setProperty('--bg-img', 'url(def-back.png)');
  }
}


// function onlight() {
//   $("#light").css("display", "none");
//   $("#dark").css("display", "flex");
//   localStorage.setItem("theme", "dark");
//   r.style.setProperty('--back', 'rgba(100, 100, 147, 1)');
//   r.style.setProperty('--navBar', 'rgba(174, 174, 255, 1)');
//   r.style.setProperty('--color1', '#4b4b6e');
//   r.style.setProperty('--color3', '#acacf9');
//   r.style.setProperty('--color2', 'rgba(100, 100, 146, 1)');
//   $("#nav-bar").css("box-shadow", "0px 0px 0px rgb(15,15,15)");
//   $(".n-center button").css("filter", "brightness(100%)");
//   $(".n-center button").css("color", "var(--color2)");
//   $(".n-center button:hover").css("color", "var(--color1)");
// }


// function ondark() {
//   if(localStorage.getItem("theme") == "dark") {
//     $("#light").css("display", "flex");
//     $("#dark").css("display", "none");
//     localStorage.setItem("theme", "light");

//     //code here...
    
//     console.log("Success");
//   }
// }


