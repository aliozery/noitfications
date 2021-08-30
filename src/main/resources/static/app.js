let stompClient = null;

// let SockJS = require('sockjs-client');
// const Stomp = require('stompjs');

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#userinfo").html("");
}

function connect() {
    const socket = new SockJS('/websocket-example');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/user', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/user", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#userinfo").append("<tr><td>" + message + "</td></tr>");
}

function send_individual(){
    $.ajax({
        type: 'POST',
        url: '/api/pushNotification/individualEmail',
        data: JSON.stringify ({email: $("#email").val(), message: $("#message").val(), subject: $("#subject").val()}),
        success: function() { alert('successfully'); },
        contentType: "application/json",
        dataType: 'json'
    });
}


$(document).ready(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#send_individual" ).click(function() { send_individual(); });
});