// scripts/sidebar.js
document.addEventListener('DOMContentLoaded', function() {
    const sidebar = document.querySelector('.sidebar');
    const sidebarTrigger = document.querySelector('.sidebar-trigger');

    sidebarTrigger.addEventListener('mouseover', function() {
        sidebar.classList.add('sidebar-visible');
    });

    sidebar.addEventListener('mouseleave', function() {
        sidebar.classList.remove('sidebar-visible');
    });
});
