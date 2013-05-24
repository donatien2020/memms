package org.chai.memms.report



import org.junit.*
import grails.test.mixin.*

@TestFor(IndicatorColorCriteriaPerDataLocationTypeController)
@Mock(IndicatorColorCriteriaPerDataLocationType)
class IndicatorColorCriteriaPerDataLocationTypeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/indicatorColorCriteriaPerDataLocationType/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.indicatorColorCriteriaPerDataLocationTypeInstanceList.size() == 0
        assert model.indicatorColorCriteriaPerDataLocationTypeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.indicatorColorCriteriaPerDataLocationTypeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.indicatorColorCriteriaPerDataLocationTypeInstance != null
        assert view == '/indicatorColorCriteriaPerDataLocationType/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/indicatorColorCriteriaPerDataLocationType/show/1'
        assert controller.flash.message != null
        assert IndicatorColorCriteriaPerDataLocationType.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/indicatorColorCriteriaPerDataLocationType/list'

        populateValidParams(params)
        def indicatorColorCriteriaPerDataLocationType = new IndicatorColorCriteriaPerDataLocationType(params)

        assert indicatorColorCriteriaPerDataLocationType.save() != null

        params.id = indicatorColorCriteriaPerDataLocationType.id

        def model = controller.show()

        assert model.indicatorColorCriteriaPerDataLocationTypeInstance == indicatorColorCriteriaPerDataLocationType
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/indicatorColorCriteriaPerDataLocationType/list'

        populateValidParams(params)
        def indicatorColorCriteriaPerDataLocationType = new IndicatorColorCriteriaPerDataLocationType(params)

        assert indicatorColorCriteriaPerDataLocationType.save() != null

        params.id = indicatorColorCriteriaPerDataLocationType.id

        def model = controller.edit()

        assert model.indicatorColorCriteriaPerDataLocationTypeInstance == indicatorColorCriteriaPerDataLocationType
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/indicatorColorCriteriaPerDataLocationType/list'

        response.reset()

        populateValidParams(params)
        def indicatorColorCriteriaPerDataLocationType = new IndicatorColorCriteriaPerDataLocationType(params)

        assert indicatorColorCriteriaPerDataLocationType.save() != null

        // test invalid parameters in update
        params.id = indicatorColorCriteriaPerDataLocationType.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/indicatorColorCriteriaPerDataLocationType/edit"
        assert model.indicatorColorCriteriaPerDataLocationTypeInstance != null

        indicatorColorCriteriaPerDataLocationType.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/indicatorColorCriteriaPerDataLocationType/show/$indicatorColorCriteriaPerDataLocationType.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        indicatorColorCriteriaPerDataLocationType.clearErrors()

        populateValidParams(params)
        params.id = indicatorColorCriteriaPerDataLocationType.id
        params.version = -1
        controller.update()

        assert view == "/indicatorColorCriteriaPerDataLocationType/edit"
        assert model.indicatorColorCriteriaPerDataLocationTypeInstance != null
        assert model.indicatorColorCriteriaPerDataLocationTypeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/indicatorColorCriteriaPerDataLocationType/list'

        response.reset()

        populateValidParams(params)
        def indicatorColorCriteriaPerDataLocationType = new IndicatorColorCriteriaPerDataLocationType(params)

        assert indicatorColorCriteriaPerDataLocationType.save() != null
        assert IndicatorColorCriteriaPerDataLocationType.count() == 1

        params.id = indicatorColorCriteriaPerDataLocationType.id

        controller.delete()

        assert IndicatorColorCriteriaPerDataLocationType.count() == 0
        assert IndicatorColorCriteriaPerDataLocationType.get(indicatorColorCriteriaPerDataLocationType.id) == null
        assert response.redirectedUrl == '/indicatorColorCriteriaPerDataLocationType/list'
    }
}
