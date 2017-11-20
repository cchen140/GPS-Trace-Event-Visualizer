<!DOCTYPE html>
<html lang="en">
<head>
    <!--  Required meta  tags -->
    <meta charset="utf-8">

    <script type="text/javascript" src="//maps.google.com/maps/api/js?sensor=true"></script>
    <script type="text/javascript" src="js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="js/gmaps.js"></script>

    <link href='css/index.css' rel='stylesheet' type='text/css' />

    <title>GPS Event Visualizer</title>

    <script type="text/javascript">
        var map;
        $(document).ready(function(){
            map = new GMaps({
                div: '#map-wrapper',
                // 40.049770, -86.922989 // Sugar Creek
                // Rout link https://www.google.com/maps/dir/40.0565233,-86.9316414/40.0428028,-86.911584/@40.0490698,-86.9165937,14z/data=!4m2!4m1!3e2
                // 40.052839, -86.903916
                // https://www.google.com/maps/dir/40.0572946,-86.908352/40.0407672,-86.9014014/@40.05001,-86.9036856,15z/data=!4m2!4m1!3e2
                // 40.118779, -90.343393
                // https://www.google.com/maps/dir/40.1298784,-90.3409376/40.1100606,-90.3408696/@40.1217214,-90.3345378,15z/data=!4m2!4m1!3e2
                lat: 40.118779,
                lng: -90.343393,
                click: function(e){
                    console.log(e);
                },
                disableDefaultUI: true,
                styles: [
                    {
                        "elementType": "labels",
                        "stylers": [
                            {
                                "visibility": "off"
                            }
                        ]
                    },
                    {
                        "featureType": "administrative",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "visibility": "off"
                            }
                        ]
                    },
                    {
                        "featureType": "administrative.land_parcel",
                        "stylers": [
                            {
                                "visibility": "off"
                            }
                        ]
                    },
                    {
                        "featureType": "administrative.neighborhood",
                        "stylers": [
                            {
                                "visibility": "off"
                            }
                        ]
                    },
                    {
                        "featureType": "poi",
                        "stylers": [
                            {
                                "visibility": "off"
                            }
                        ]
                    },
                    {
                        "featureType": "road",
                        "stylers": [
                            {
                                "visibility": "off"
                            }
                        ]
                    },
                    {
                        "featureType": "road",
                        "elementType": "labels.icon",
                        "stylers": [
                            {
                                "visibility": "off"
                            }
                        ]
                    },
                    {
                        "featureType": "transit",
                        "stylers": [
                            {
                                "visibility": "off"
                            }
                        ]
                    }
                ]
            });

//            path = [[-12.044012922866312, -77.02470665341184], [-12.05449279282314, -77.03024273281858], [-12.055122327623378, -77.03039293652341], [-12.075917129727586, -77.02764635449216], [-12.07635776902266, -77.02792530422971], [-12.076819390363665, -77.02893381481931], [-12.088527520066453, -77.0241058385925], [-12.090814532191756, -77.02271108990476]];
//
//            map.drawPolyline({
//                path: path,
//                strokeColor: '#131540',
//                strokeOpacity: 0.6,
//                strokeWeight: 6
//            });

            $( "#btn-reset" ).click(function() {
                $.ajax({
                    url: "./reset",
                    type: "GET",
                    data: {
                        cmd: "reset"
                    },
                    dataType: "json",
                    success: function (result) {
                        console.log(result);
                        lastTimestamp = 0;
                        lastPathWaypoint = [0, 0];
                    }
                });
            });
        });

        var lastTimestamp = 0;
        var lastPathWaypoint = [0,0];
        var oneSecondTimerEvent = setInterval(function() {
            //console.log("test");

            $.ajax({
                url: "./getNewData",
                type: "GET",
                data: {
                    timestamp: lastTimestamp
                },
                dataType: "json",
                success: function(result) {
                    /* Markers */
                    if (result["markers"].length != 0) {

                        //$("#div1").html(result);
                        $.each(result["markers"], function (key, value) {
                            //console.log(key + ":" + value);

                            if (value["mode"] == "1") {
                                console.log("1");
                                map.addMarker({
                                    lat: value["lat"],
                                    lng: value["lng"],
                                    //title: 'Lima',
                                    icon: {
                                        path: google.maps.SymbolPath.CIRCLE,
                                        scale: 6,
                                        //fillColor: 'red',
                                        strokeColor: 'green',
                                        strokeWeight: 6
                                    }
                                });
                            } else {
                                console.log("2");
                                map.addMarker({
                                    lat: value["lat"],
                                    lng: value["lng"],
                                    //title: 'Lima',
                                });
                            }

                            if (value["timestamp"] > lastTimestamp) {
                                lastTimestamp = value["timestamp"];
                            }
                        });
                    }

                    /* Paths */
                    if (result["paths"].length != 0) {

                        var newPaths = [];

                        if (lastPathWaypoint[0] != 0) {
                            newPaths.push(lastPathWaypoint);
                        }

                        $.each(result["paths"], function (key, value) {
                            //console.log(key + ":" + value);

                            newPaths.push([value["lat"], value["lng"]]);
                            if (value["timestamp"] > lastTimestamp) {
                                lastTimestamp = value["timestamp"];
                            }
                        });

                        // Update last waypoint
                        lastPathWaypoint[0] = newPaths[newPaths.length - 1][0];
                        lastPathWaypoint[1] = newPaths[newPaths.length - 1][1];

                        console.log(newPaths);

                        map.drawPolyline({
                            path: newPaths,
                            strokeColor: '#131540',
                            strokeOpacity: 1,
                            strokeWeight: 6
                        });
                    }

                }
            });

            // To stop the timer:
            // clearInterval(interval);
        }, 300);


    </script>
</head>


<!-- <body> -->
<body>
<!--     <h1>Hello, world!</h1> -->

<div class="site-wrapper">
    <div class="site-wrapper-inner">

        <div id="map-wrapper">

        </div>
        <div id="control-pane-wrapper">
            <button id="btn-reset" type="button">Reset</button>
        </div>

    </div>
</div>
</body>
</html>