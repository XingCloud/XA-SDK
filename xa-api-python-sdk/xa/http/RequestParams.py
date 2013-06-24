from xa.model.Operation import Operation


class RequestParams:

    def __init__(self, params):
        """

        :param params:
        """
        self.params = {}
        if isinstance(params,list):
            operations = params
        else:
            operations = [params]
            
        count = 0
        for operation in operations:
            if isinstance(operation, Operation):
                key = operation.getOperationName(count)
                value = operation.getOperationValue();
                count += 1
                self.params[key] = value

    def addParam(self, key, value):
        self.params[key] = value

    def removeParam(self, key):
        del self.params[key]

    def toQueryString(self):
        first = True
        queryString = ''
        for key in self.params.keys():
            if not first:
                queryString += '&'
            queryString += key + '=' + self.params[key]
            first = False
        return queryString
            
        
                
            
