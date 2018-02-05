angular.module('os.administrative.order.cost', [])
  .filter('distCostTotal', function() {
    return function(orderItems) {
      var total = 0;
      angular.forEach(orderItems,
        function(item) {
          if (isNaN(parseFloat(item.cost))) {
            return;
          }

          total += item.cost;
        }
      );

      return total;
    }
  });

