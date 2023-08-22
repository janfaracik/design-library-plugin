const searchBarInput = document.querySelector("#design-library-search-bar");

searchBarInput.suggestions = function () {
  return [...document.querySelectorAll(".task-link")]
    .map((item) => ({
      url: item.href,
      icon: item.querySelector(
        ".task-icon-link"
      ).innerHTML,
      label: item.querySelector(".task-link-text").textContent,
    }));
};

const sidebarHeaders = document.querySelectorAll(".sidebartitle");

sidebarHeaders.forEach(header => {

    header.addEventListener("click", () => {
        const group = header.nextSibling;
        console.log(group)

        header.dataset.expandedState = header.dataset.expandedState === 'open' ? 'closed' : 'open';
        group.dataset.expandedState = group.dataset.expandedState === 'open' ? 'closed' : 'open';

    })


})
