
angular.module('os.query.executor', [])
  .factory('QueryExecutor', function($http, $document, Util, ApiUrls) {
    var queryUrl = ApiUrls.getBaseUrl() + 'query';

    return {
      getCount: function(queryId, cpId, aql) {
        var req = {
          savedQueryId: queryId, 
          cpId: cpId, 
          drivingForm: 'Participant',
          runType: 'Count',
          aql: aql
        };

        return $http.post(queryUrl, req).then(
          function(resp) {
            var data = resp.data;

            var result = {cprCnt: 0, specimenCnt: 0};
            result.cprCnt  = data.rows[0][0];
            result.visitCnt = data.rows[0][1];
            for (var i = 2; i < data.rows[0].length; ++i) {
              result.specimenCnt += parseInt(data.rows[0][i]);
            }
            return result;
          }
        );
      },

      getRecords: function(queryId, cpId, aql, wideRowMode, outputIsoFmt, opts) {
        opts = opts || {};

        var req = {
          savedQueryId: queryId, 
          cpId: cpId,
          drivingForm: 'Participant',
          runType: 'Data', 
          aql: aql, 
          wideRowMode: wideRowMode || "OFF",
          outputIsoDateTime: (outputIsoFmt || false),
          outputColumnExprs: opts.outputColumnExprs || false
        };
        return $http.post(queryUrl, req).then(
          function(resp) {
            return resp.data;
          }
        );
      },

      exportQueryResultsData: function(queryId, cpId, aql, wideRowMode, opts) {
        opts = opts || {};

        var req = {
          savedQueryId: queryId,
          cpId: cpId,
          drivingForm: 'Participant',
          runType: 'Export',
          aql: aql,
          indexOf: 'Specimen.label',
          wideRowMode: wideRowMode || "OFF",
          outputColumnExprs: opts.outputColumnExprs || false
        };

        return $http.post(queryUrl + '/export', req).then(
          function(resp) {
            return resp.data;
          }
        );
      },

      downloadDataFile: function(fileId, filename) {
        filename = !!filename ? filename : 'QueryResults.csv';
        Util.downloadFile(queryUrl + '/export?fileId=' + fileId + '&filename=' + filename);
      },

      getFacetValues: function(cpId, facetExprs, searchTerm, restriction) {
        var op = {cpId: cpId, facets: facetExprs, searchTerm: searchTerm, restriction: restriction};
        return $http.post(queryUrl + '/facet-values', op).then(
          function(result) {
            return result.data;
          }
        );
      }
    };
  });
