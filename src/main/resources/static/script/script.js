var $http = axios.create({
    headers: {
        'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').getAttribute('value')
    }
});