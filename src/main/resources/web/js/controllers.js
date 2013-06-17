'use strict';

function GabblerCtrl($scope, Message) {

  $scope.messages = [];

  $scope.getMessages = function() {
    var messages = Message.query(function() {
      $scope.messages = messages.concat($scope.messages);
      $scope.getMessages();
    });
  }

  $scope.message = new Message({ "username": "" });

  $scope.sendMessage = function() {
    $scope.message.$save();
    $scope.message = new Message({ "username": "" });
  };
}
