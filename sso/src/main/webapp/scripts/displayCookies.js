window.onload = function() {
    const cookies = document.cookie.split('; ').map(cookie => cookie.split('='));
    const cookieInfoDiv = document.getElementById('cookieInfo');
    cookieInfoDiv.innerHTML = ''; // 清空之前的显示内容

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

