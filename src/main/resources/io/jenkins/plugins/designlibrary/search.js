const searchBarInput = document.querySelector("#design-library-search-bar");
let suggestions = [];

const fullUrl = `/jenkins/design-library/search`;
fetch(fullUrl)
    .then(response => response.json())
    .then(json => suggestions = mapItems(json['data']));

function mapItems(items) {
    return items
        .map((item) => ({
            url: item.url,
            icon: item.icon,
            label: item.title,
            children: mapItems(item.children) ?? []
        }));
}

searchBarInput.suggestions = function () {
    return suggestions;
};
