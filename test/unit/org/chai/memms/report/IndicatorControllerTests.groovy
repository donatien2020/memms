package org.chai.memms.report



import org.junit.*
import grails.test.mixin.*

@TestFor(IndicatorController)
@Mock(Indicator)
class IndicatorControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/indicator/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.indicatorInstanceList.size() == 0
        assert model.indicatorInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.indicatorInstance != null
    }

    void testSave() {
        controller.save()

        assert model.indicatorInstance != null
        assert view == '/indicator/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/indicator/show/1'
        assert controller.flash.message != null
        assert Indicator.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/indicator/list'

        populateValidParams(params)
        def indicator = new Indicator(params)

        assert indicator.save() != null

        params.id = indicator.id

        def model = controller.show()

        assert model.indicatorInstance == indicator
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/indicator/list'

        populateValidParams(params)
        def indicator = new Indicator(params)

        assert indicator.save() != null

        params.id = indicator.id

        def model = controller.edit()

        assert model.indicatorInstance == indicator
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/indicator/list'

        response.reset()

        populateValidParams(params)
        def indicator = new Indicator(params)

        assert indicator.save() != null

        // test invalid parameters in update
        params.id = indicator.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/indicator/edit"
        assert model.indicatorInstance != null

        indicator.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/indicator/show/$indicator.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        indicator.clearErrors()

        populateValidParams(params)
        params.id = indicator.id
        params.version = -1
        controller.update()

        assert view == "/indicator/edit"
        assert model.indicatorInstance != null
        assert model.indicatorInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/indicator/list'

        response.reset()

        populateValidParams(params)
        def indicator = new Indicator(params)

        assert indicator.save() != null
        assert Indicator.count() == 1

        params.id = indicator.id

        controller.delete()

        assert Indicator.count() == 0
        assert Indicator.get(indicator.id) == null
        assert response.redirectedUrl == '/indicator/list'
    }
}
