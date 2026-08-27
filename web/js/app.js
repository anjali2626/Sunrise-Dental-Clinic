document.addEventListener("DOMContentLoaded", function () {

    const currentPage =
        window.location.pathname.split("/").pop();

    const links =
        document.querySelectorAll(".nav-link");

    links.forEach(function (link) {

        const href =
            link.getAttribute("href");

        if (href === currentPage) {
            link.classList.add("active");
        }

    });

});