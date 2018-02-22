angular.module('os.administrative.site.addedit', ['os.administrative.models'])
  .controller('SiteAddEditCtrl', function(
    $scope, $state, site, extensionCtxt, currentUser, Institute, ExtensionsUtil) {

    var defInstitutes;

    function init() {
      $scope.site = site;
      if (!currentUser.admin && !site.id) {
        site.instituteName = currentUser.instituteName;
      }

      $scope.deFormCtrl = {};
      $scope.extnOpts = ExtensionsUtil.getExtnOpts(site, extensionCtxt);
      $scope.coordFilterOpts = {institute: $scope.site.instituteName};
      
      if (currentUser.admin) {
        loadPvs();
      }
    }

    function loadPvs() {
      $scope.institutes = [];
      loadInstitutes();
    }

    function loadInstitutes(searchString) {
      if (defInstitutes && !searchString) {
        $scope.institutes = defInstitutes;
        return;
      }

      if (defInstitutes && defInstitutes.length < 100) {
        return;
      }

      Institute.query({name: searchString}).then(
        function(institutes) {
          $scope.institutes = institutes.map(function(institute) { return institute.name });
          if (!searchString) {
            defInstitutes = $scope.institutes;
          }
        }
      );
    }

    $scope.loadInstitutes = loadInstitutes;
    
    $scope.onInstituteSelect = function() {
      $scope.site.coordinators = undefined;
      $scope.coordFilterOpts = {institute: $scope.site.instituteName};
    }

    $scope.save = function() {
      var formCtrl = $scope.deFormCtrl.ctrl;
      if (formCtrl && !formCtrl.validate()) {
        return;
      }

      var site = angular.copy($scope.site);
      if (formCtrl) {
        site.extensionDetail = formCtrl.getFormData();
      }

      site.$saveOrUpdate().then(
        function(savedSite) {
          $state.go('site-detail.overview', {siteId: savedSite.id});
        }
      );
    }

    init();
  });
