package org.chai.memms.report



import org.junit.*
import grails.test.mixin.*

@TestFor(IntermediateVariableController)
@Mock(IntermediateVariable)
class IntermediateVariableControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/intermediateVariable/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.intermediateVariableInstanceList.size() == 0
        assert model.intermediateVariableInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.intermediateVariableInstance != null
    }

    void testSave() {
        controller.save()

        assert model.intermediateVariableInstance != null
        assert view == '/intermediateVariable/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/intermediateVariable/show/1'
        assert controller.flash.message != null
        assert IntermediateVariable.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/intermediateVariable/list'

        populateValidParams(params)
        def intermediateVariable = new IntermediateVariable(params)

        assert intermediateVariable.save() != null

        params.id = intermediateVariable.id

        def model = controller.show()

        assert model.intermediateVariableInstance == intermediateVariable
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/intermediateVariable/list'

        populateValidParams(params)
        def intermediateVariable = new IntermediateVariable(params)

        assert intermediateVariable.save() != null

        params.id = intermediateVariable.id

        def model = controller.edit()

        assert model.intermediateVariableInstance == intermediateVariable
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/intermediateVariable/list'

        response.reset()

        populateValidParams(params)
        def intermediateVariable = new IntermediateVariable(params)

        assert intermediateVariable.save() != null

        // test invalid parameters in update
        params.id = intermediateVariable.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/intermediateVariable/edit"
        assert model.intermediateVariableInstance != null

        intermediateVariable.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/intermediateVariable/show/$intermediateVariable.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        intermediateVariable.clearErrors()

        populateValidParams(params)
        params.id = intermediateVariable.id
        params.version = -1
        controller.update()

        assert view == "/intermediateVariable/edit"
        assert model.intermediateVariableInstance != null
        assert model.intermediateVariableInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/intermediateVariable/list'

        response.reset()

        populateValidParams(params)
        def intermediateVariable = new IntermediateVariable(params)

        assert intermediateVariable.save() != null
        assert IntermediateVariable.count() == 1

        params.id = intermediateVariable.id

        controller.delete()

        assert IntermediateVariable.count() == 0
        assert IntermediateVariable.get(intermediateVariable.id) == null
        assert response.redirectedUrl == '/intermediateVariable/list'
    }
}
