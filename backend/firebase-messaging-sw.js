importScripts('https://www.gstatic.com/firebasejs/6.0.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/6.0.2/firebase-messaging.js');
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

var messaging = firebase.messaging();

messaging.setBackgroundMessageHandler(function(payload) {
    console.log('[firebase-messaging-sw.js] Received background message ', payload);
    // Customize notification here
    var notificationTitle = payload.data.title;
    var notificationOptions = {
        body: payload.data.body,
        icon: payload.data.icon
    };

    return self.registration.showNotification(notificationTitle,
        notificationOptions);
});