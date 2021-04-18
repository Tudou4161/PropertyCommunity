//마커클러스터링은 추후에 ...^^
var mapOptions = {
    center: new naver.maps.LatLng(37.3595704, 127.105399),
    zoom: 5,
    mapTypeControl: true,
    zoomControl: true, //줌 컨트롤의 표시 여부
    zoomControlOptions: { //줌 컨트롤의 옵션
        position: naver.maps.Position.TOP_RIGHT
    }
};

var map = new naver.maps.Map("map", mapOptions);

$.ajax({
    url: "/geoinfo",
    method: "GET",
    dataType: "json"
}).done(function (data) {
    console.log(data);

    let markerList = [];
    let infoWindowList = [];

    const getClickHandler = (i) => () => {
        const marker = markerList[i];
        const infowindow = infoWindowList[i];
        if (infowindow.getMap()) {
            infowindow.close(); // 인포윈도우 닫기
        } else {
            infowindow.open(map, marker); // 인포윈도우 열기
        }
    };

    const getClickMap = (i) => () => {
        const infowindow = infoWindowList[i];
        infowindow.close();
    };


    for (let i in data) {
        let target = data[i];
        for(let j = 0; j < target.length; j++ ) {
            const latlng = new naver.maps.LatLng(target[j]["lat"], target[j]["lng"]);

            let marker = new naver.maps.Marker({
                map: map,
                position: latlng,
                icon : {
                    content: `<div class="marker"></div>`,
                    anChor: new naver.maps.Point(7.5, 7.5),
                },
            });

            let postId = target[j]["postId"];
            let roadAddr = target[j]["roadAddr"];
            let subAddr = target[j]["subAddr"];
            let avg_grade = target[j]["avg_grade"];


            const contents =
                `<div class="infowindow_wrap">
                    <div class="infowindow_title"> 주소: ${roadAddr}</div>
                    <div class="infowindow_title"> 상호명: ${subAddr}</div>
                    <div class="infowindow_title"> 평점: ${avg_grade}</div>
                    <button id="href-click" class="infowindow_title" value="${postId}" 
                    onclick="clickInfoWindow()">
                        클릭
                    </button>
                 </div>`;


            const infowindow = new naver.maps.InfoWindow({
                content: contents,
                backgroundColor: "#00ff0000",
                borderColor: "#00ff0000",
                anChorSize: new naver.maps.Size(0, 0),
            });

            markerList.push(marker);
            infoWindowList.push(infowindow);
        }
    }

    for (let i = 0, ii = markerList.length; i < ii; i++) {
        naver.maps.Event.addListener(markerList[i], "click", getClickHandler(i));
        naver.maps.Event.addListener(map, "click", getClickMap(i));
    }

    const cluster1 = {
        content : `<div class="cluster1"></div>`,
    };

    const cluster2 = {
        content : `<div class="cluster2"></div>`,
    };

    const cluster3 = {
        content : `<div class="cluster3"></div>`,
    };

    const markerClustering = new MarkerClustering({
        minClusterSize : 2,
        maxZoom: 12,
        map: map,
        markers: markerList,
        disableClickZoom: false,
        gridSize: 20,
        icons: [cluster1, cluster2, cluster3],
        indexGenerator: [2, 5, 10],
        stylingFunction: (clusterMarker, count) => {
            $(clusterMarker.getElement()).find("div:first-child").text(count);
        },
    });
});

//지도 검색 옵션 js코드
map.setCursor('pointer');

function searchAddressToCoordinate(address) {
    naver.maps.Service.geocode({
        query: address
    }, function(status, response) {
        if (status === naver.maps.Service.Status.ERROR) {
            return alert('Something Wrong!');
        }

        var htmlAddresses = [],
            item = response.v2.addresses[0],
            point = new naver.maps.Point(item.x, item.y);

        //검색위치로 이동하면 줌을 level12로 준다.
        map.setCenter(point);
        map.setZoom(14);
    });
}

function initGeocoder() {
    if (!map.isStyleMapReady) {
        return;
    }


    $('#address').on('keydown', function(e) {
        var keyCode = e.which;

        if (keyCode === 13) { // Enter Key
            searchAddressToCoordinate($('#address').val());
        }
    });

    $('#submit').on('click', function(e) {
        e.preventDefault();

        searchAddressToCoordinate($('#address').val());
    });

    //searchAddressToCoordinate('정자동 178-1');
}

naver.maps.onJSContentLoaded = initGeocoder;
naver.maps.Event.once(map, 'init_stylemap', initGeocoder);


//인포윈도우를 클릭했을 때, 오른쪽에 상세정보 창이 뜰 수 있도록 코드를 짤 예정.
function clickInfoWindow() {
        $('#href-click').on("click", function () {
            var postId = $("#href-click").attr("value");

            $.ajax({
                type: "GET",
                url: '/maps/map/detail/' + Number(postId),
                dataType: "json"
            }).done(function (data) {
                console.log(data);
                // $("#sidebar").text(data);
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        });
};
