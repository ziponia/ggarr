var $http = axios.create({
    headers: {
        'X-CSRF-TOKEN': document.getElementById('_csrf').getAttribute('value')
    }
});