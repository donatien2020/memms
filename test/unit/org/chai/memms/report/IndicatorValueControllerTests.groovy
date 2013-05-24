package org.chai.memms.report



import org.junit.*
import grails.test.mixin.*

@TestFor(IndicatorValueController)
@Mock(IndicatorValue)
class IndicatorValueControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/indicatorValue/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.indicatorValueInstanceList.size() == 0
        assert model.indicatorValueInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.indicatorValueInstance != null
    }

    void testSave() {
        controller.save()

        assert model.indicatorValueInstance != null
        assert view == '/indicatorValue/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/indicatorValue/show/1'
        assert controller.flash.message != null
        assert IndicatorValue.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/indicatorValue/list'

        populateValidParams(params)
        def indicatorValue = new IndicatorValue(params)

        assert indicatorValue.save() != null

        params.id = indicatorValue.id

        def model = controller.show()

        assert model.indicatorValueInstance == indicatorValue
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/indicatorValue/list'

        populateValidParams(params)
        def indicatorValue = new IndicatorValue(params)

        assert indicatorValue.save() != null

        params.id = indicatorValue.id

        def model = controller.edit()

        assert model.indicatorValueInstance == indicatorValue
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/indicatorValue/list'

        response.reset()

        populateValidParams(params)
        def indicatorValue = new IndicatorValue(params)

        assert indicatorValue.save() != null

        // test invalid parameters in update
        params.id = indicatorValue.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/indicatorValue/edit"
        assert model.indicatorValueInstance != null

        indicatorValue.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/indicatorValue/show/$indicatorValue.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        indicatorValue.clearErrors()

        populateValidParams(params)
        params.id = indicatorValue.id
        params.version = -1
        controller.update()

        assert view == "/indicatorValue/edit"
        assert model.indicatorValueInstance != null
        assert model.indicatorValueInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/indicatorValue/list'

        response.reset()

        populateValidParams(params)
        def indicatorValue = new IndicatorValue(params)

        assert indicatorValue.save() != null
        assert IndicatorValue.count() == 1

        params.id = indicatorValue.id

        controller.delete()

        assert IndicatorValue.count() == 0
        assert IndicatorValue.get(indicatorValue.id) == null
        assert response.redirectedUrl == '/indicatorValue/list'
    }
}
