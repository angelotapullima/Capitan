<html>
<head>
    <title>Test Firebase</title>
    <link rel="manifest" href="manifest.json">
    </head>
<body>
<h1>Testing</h1>
<!-- The core Firebase JS SDK is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/6.0.2/firebase.js"></script>
<script src="js/jquery-3.3.1.min.js"></script>
<!-- TODO: Add SDKs for Firebase products that you want to use
     https://firebase.google.com/docs/web/setup#config-web-app -->
<script>
    // Your web app's Firebase configuration
    var firebaseConfig = {
        apiKey: "AIzaSyDx4245-y1EJriza2lbdVe7EzdHmGwFAGI",
        authDomain: "sipci-d8319.firebaseapp.com",
        databaseURL: "https://sipci-d8319.firebaseio.com",
        projectId: "sipci-d8319",
        storageBucket: "sipci-d8319.appspot.com",
        messagingSenderId: "505767571836",
        appId: "1:505767571836:web:f556ac7d61d4b245"
    };
    // Initialize Firebase
    firebase.initializeApp(firebaseConfig);
    const messaging = firebase.messaging();
    messaging.requestPermission().then(function() {
        console.log('Notification permission granted.');
        if(isTokenSentToServer()){
            console.log("TOken already saved");
        }else{
            getRegToken();
        }
    }).catch(function(err) {
        console.log('Unable to get permission to notify.', err);
    });
    function getRegToken(argument) {
        // Get Instance ID token. Initially this makes a network call, once retrieved
        // subsequent calls to getToken will return from cache.
        messaging.getToken().then(function(currentToken) {
            if (currentToken) {
                saveToken(currentToken);
                console.log(currentToken);
                setTokenSentToServer(true);
            } else {
                // Show permission request.
                console.log('No Instance ID token available. Request permission to generate one.');
                setTokenSentToServer(false);
            }
        }).catch(function(err) {
            console.log('An error occurred while retrieving token. ', err);
            showToken('Error retrieving Instance ID token. ', err);
            setTokenSentToServer(false);
        });
    }
    function setTokenSentToServer(sent) {
        window.localStorage.setItem('sentToServer', sent ? '1' : '0');
    }
    function isTokenSentToServer() {
        return window.localStorage.getItem('sentToServer') === '1';
    }
    function saveToken(currentToken) {
        $.ajax({
            url: 'action.php',
            method: 'post',
            data: 'token=' + currentToken
        }).done(function (result) {
            console.log(result);
        })
    }
    messaging.onMessage(function (payload) {
        console.log('Message Received' + payload);
        alert("Alerta!");
        notificationTitle=payload.data.title;
        notificationOptions= {
            body: payload.data.body,
            icon: payload.data.icon,
            image: payload.data.icon
        };
        var notification = new Notification(notificationTitle,notificationOptions);
    })
</script>
</body>
</html>