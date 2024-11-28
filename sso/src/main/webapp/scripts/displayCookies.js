window.onload = function() {
    const cookies = document.cookie.split('; ').map(cookie => cookie.split('='));
    const cookieInfoDiv = document.getElementById('cookieInfo');
    // cookieInfoDiv.innerHTML = ''; // 清空之前的显示内容

    if (cookies.length === 0) {
        cookieInfoDiv.textContent = 'No cookies found.';
    } else {
        cookies.forEach(([name, value]) => {
            const p = document.createElement('p');
            p.textContent = `${name}: ${value}`;
            cookieInfoDiv.appendChild(p);
        });
    }
};
// document.addEventListener('DOMContentLoaded', function () {
//     const cookieInfoDiv = document.getElementById('cookieInfo');
//     const cookies = document.cookie.split('; ');
//     let cookieText = '';
//     cookies.forEach(cookie => {
//         const [name, value] = cookie.split('=');
//         cookieText += `${name}: ${truncateString(value, 50)}<br>`;
//     });
//     cookieInfoDiv.innerHTML = cookieText;
// });

// document.getElementById('showCookies').addEventListener('click', function() {
//     const cookies = document.cookie.split('; ').map(cookie => cookie.split('='));
//     const cookieInfoDiv = document.getElementById('cookieInfo');
//     cookieInfoDiv.innerHTML = ''; // 清空之前的显示内容
//
//     if (cookies.length === 0) {
//         cookieInfoDiv.textContent = 'No cookies found.';
//     } else {
//         cookies.forEach(([name, value]) => {
//             const p = document.createElement('p');
//             p.textContent = `${name}: ${value}`;
//             cookieInfoDiv.appendChild(p);
//         });
//     }
// });

