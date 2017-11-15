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
                lat: -12.043333,
                lng: -77.028333,
                click: function(e){
                    console.log(e);
                }
            });

            path = [[-12.044012922866312, -77.02470665341184], [-12.05449279282314, -77.03024273281858], [-12.055122327623378, -77.03039293652341], [-12.075917129727586, -77.02764635449216], [-12.07635776902266, -77.02792530422971], [-12.076819390363665, -77.02893381481931], [-12.088527520066453, -77.0241058385925], [-12.090814532191756, -77.02271108990476]];

            map.drawPolyline({
                path: path,
                strokeColor: '#131540',
                strokeOpacity: 0.6,
                strokeWeight: 6
            });
        });

        var lastTimestamp = 0;
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
                    //$("#div1").html(result);
                    $.each(result["markers"], function(key, value) {
                        //console.log(key + ":" + value);

                        map.addMarker({
                            lat: value["lat"],
                            lng: value["lng"]
                            //title: 'Lima',
                        });

                        if(value["timestamp"] > lastTimestamp) {
                            lastTimestamp = value["timestamp"];
                        }
                    });

                    var newPaths = [];
                    $.each(result["paths"], function(key, value) {
                        //console.log(key + ":" + value);

                        newPaths.push([value["lat"], value["lng"]]);
                        if(value["timestamp"] > lastTimestamp) {
                            lastTimestamp = value["timestamp"];
                        }
                    });
                    console.log(newPaths);

                    map.drawPolyline({
                        path: newPaths,
                        strokeColor: '#131540',
                        strokeOpacity: 0.6,
                        strokeWeight: 6
                    });



                }
            });

            // To stop the timer:
            // clearInterval(interval);
        }, 1000);
    </script>
</head>


<!-- <body> -->
<body>
<!--     <h1>Hello, world!</h1> -->

<div class="site-wrapper">
    <div class="site-wrapper-inner">

        <div id="map-wrapper">

        </div>

    </div>
</div>
</body>
</html>