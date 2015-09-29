
angular.module('os.administrative.order.addedit', ['os.administrative.models', 'os.biospecimen.models'])
  .controller('OrderAddEditCtrl', function(
    $scope, $state, $translate, order, Institute,
    Specimen, SpecimensHolder, Site, DistributionProtocol, DistributionOrder, Alerts) {
    
    function init() {
      $scope.order = order;
      $scope.dpList = [];
      $scope.instituteNames = [];
      $scope.siteList = [];
      $scope.userFilterOpts = {};
      $scope.input = {labelText: ''};

      loadItemStatusPvs();
      loadDps();
      loadInstitutes();
      setUserAndSiteList(order);

      if (!order.id && angular.isArray(SpecimensHolder.getSpecimens())) {
        order.orderItems = getOrderItems(SpecimensHolder.getSpecimens());
        SpecimensHolder.setSpecimens(null);
      }
      
      if (!order.executionDate) {
        order.executionDate = new Date();
      }
    }

    function loadItemStatusPvs() {
      $translate('common.home').then(
        function() {
          $scope.orderItemStatuses = DistributionOrder.getItemStatusPvs().map(
            function(status) {
              return {id: status, name: $translate.instant('orders.item_statuses.' + status)};
            }
          );
        }
      );
    }

    function loadDps(name) {
      var filterOpts = {activityStatus: 'Active', query: name};
      DistributionProtocol.query(filterOpts).then(
        function(dps) {
          $scope.dpList = dps;
        }
      );
    }
    
    function loadInstitutes () {
      Institute.query().then(
        function (institutes) {
          $scope.instituteNames = Institute.getNames(institutes);
        }
      );
    }

    function loadSites(instituteName) {
      Site.listForInstitute(instituteName, true).then(
        function(sites) {
          $scope.siteList = sites;
        }
      );    
    }
    
    function setUserFilterOpts(institute) {
      $scope.userFilterOpts = {institute: institute};
    }

    function setUserAndSiteList(order) {
      var instituteName = order.instituteName;
      setUserFilterOpts(instituteName);
      loadSites(instituteName);
    }

    function getOrderItems(specimens) {
      return specimens.map(
        function(specimen) {
          return {
            specimen: specimen,
            quantity: specimen.availableQty,
            status: 'DISTRIBUTED_AND_CLOSED'
          };
        }
      );
    }

    function saveOrUpdate(order) {
      order.siteId = undefined;
      angular.copy(order).$saveOrUpdate().then(
        function(savedOrder) {
          $state.go('order-detail.overview', {orderId: savedOrder.id});
        }
      );
    };

    $scope.onDpSelect = function() {
      $scope.order.instituteName = $scope.order.distributionProtocol.instituteName;
      $scope.order.siteName = $scope.order.distributionProtocol.defReceivingSiteName;
      $scope.order.requester = $scope.order.distributionProtocol.principalInvestigator;
      setUserAndSiteList($scope.order);
    }
    
    $scope.onInstSelect = function () {
      var instName = $scope.order.instituteName;
      loadSites(instName);
      setUserFilterOpts(instName);
      $scope.order.siteName = '';
      $scope.order.requester = '';
    }

    $scope.addSpecimens = function() {
      var labels = 
        $scope.input.labelText.split(/,|\t|\n/)
          .map(function(label) { return label.trim(); })
          .filter(function(label) { return label.length != 0; });

      if (labels.length == 0) {
        return; 
      }

      angular.forEach(order.orderItems, function(item) {
        var idx = labels.indexOf(item.specimen.label);
        if (idx != -1) {
          labels.splice(idx, 1);
        }
      });

      if (labels.length == 0) {
        return;
      }

      Specimen.listForDp(labels, $scope.order.distributionProtocol.id).then(
        function (specimens) {
          order.orderItems = order.orderItems.concat(getOrderItems(specimens));
          $scope.input.labelText = '';
        }
      );
    }

    $scope.removeOrderItem = function(orderItem) {
      var idx = order.orderItems.indexOf(orderItem);
      if (idx != -1) {
        order.orderItems.splice(idx, 1);
      }
    }

    $scope.distribute = function() {
      $scope.order.status = 'EXECUTED';
      saveOrUpdate($scope.order);
    }

    $scope.saveDraft = function() {
      $scope.order.status = 'PENDING';
      saveOrUpdate($scope.order);
    }

    $scope.passThrough = function() {
      return true;
    }
    
    $scope.searchDp = loadDps;
    
    init();
  });
