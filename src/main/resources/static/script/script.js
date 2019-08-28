var $http = axios.create({
    headers: {
        'X-CSRF-TOKEN': document.getElementById('_csrf').getAttribute('value')
    }
});

var moment = moment.tz.setDefault('Asia/Seoul');
moment.locale('ko');
// moment.updateLocale('ko', {
//     relativeTime : {
//         future: "in %s",
//         past:   "%s 전",
//         s  : '몇 초 전',
//         ss : '%d 초 전',
//         m:  "1 분전",
//         mm: "%d 분 전",
//         h:  "1시간 전",
//         hh: "%d 시간 전",
//         d:  "어제",
//         dd: "%d 일 전",
//         M:  "저번달",
//         MM: "%d 달 전",
//         y:  "작년 이맘때",
//         yy: "%d 년전"
//     }
// });