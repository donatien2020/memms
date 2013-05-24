package org.chai.memms.report



import org.junit.*
import grails.test.mixin.*

@TestFor(QueryParserHelperController)
@Mock(QueryParserHelper)
class QueryParserHelperControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/queryParserHelper/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.queryParserHelperInstanceList.size() == 0
        assert model.queryParserHelperInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.queryParserHelperInstance != null
    }

    void testSave() {
        controller.save()

        assert model.queryParserHelperInstance != null
        assert view == '/queryParserHelper/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/queryParserHelper/show/1'
        assert controller.flash.message != null
        assert QueryParserHelper.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/queryParserHelper/list'

        populateValidParams(params)
        def queryParserHelper = new QueryParserHelper(params)

        assert queryParserHelper.save() != null

        params.id = queryParserHelper.id

        def model = controller.show()

        assert model.queryParserHelperInstance == queryParserHelper
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/queryParserHelper/list'

        populateValidParams(params)
        def queryParserHelper = new QueryParserHelper(params)

        assert queryParserHelper.save() != null

        params.id = queryParserHelper.id

        def model = controller.edit()

        assert model.queryParserHelperInstance == queryParserHelper
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/queryParserHelper/list'

        response.reset()

        populateValidParams(params)
        def queryParserHelper = new QueryParserHelper(params)

        assert queryParserHelper.save() != null

        // test invalid parameters in update
        params.id = queryParserHelper.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/queryParserHelper/edit"
        assert model.queryParserHelperInstance != null

        queryParserHelper.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/queryParserHelper/show/$queryParserHelper.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        queryParserHelper.clearErrors()

        populateValidParams(params)
        params.id = queryParserHelper.id
        params.version = -1
        controller.update()

        assert view == "/queryParserHelper/edit"
        assert model.queryParserHelperInstance != null
        assert model.queryParserHelperInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/queryParserHelper/list'

        response.reset()

        populateValidParams(params)
        def queryParserHelper = new QueryParserHelper(params)

        assert queryParserHelper.save() != null
        assert QueryParserHelper.count() == 1

        params.id = queryParserHelper.id

        controller.delete()

        assert QueryParserHelper.count() == 0
        assert QueryParserHelper.get(queryParserHelper.id) == null
        assert response.redirectedUrl == '/queryParserHelper/list'
    }
}
