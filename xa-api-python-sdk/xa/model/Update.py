from xa.model.Operation import Operation


class Update(Operation):
    def __init__(self, propertyName, value):
        self.propertyName = propertyName
        self.value = value

    def getOperationName(self, index):
        return 'update' + str(index)
	
    def getOperationValue(self):
        return self.propertyName + ',' + str(self.value)
