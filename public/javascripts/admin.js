var orderSocket = new WebSocket("ws://localhost:9000/ws/orders");

orderSocket.onmessage = function (event) {
  console.log(event.data)
  var data = JSON.parse(event.data)
  $('#order-table > tbody:last-child').append(
  '<td><a href="/">' + data.order.id +'</a></td>' +
  '<td>' + data.order.firstName + '</td>' +
  '<td>' + data.order.lastName + '</td>' +
  '<td>' + data.order.emailAddress + '</td>' +
  '<td>' + data.order.shippingOption + '</td>' +
  '<td>Unknown</td>' + //expected ship date not modelled
  '<td><div class="uk-badge uk-badge-success">On-time</div></td>'
  );
}
