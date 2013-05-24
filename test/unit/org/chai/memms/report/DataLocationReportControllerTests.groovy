package org.chai.memms.report



import org.junit.*
import grails.test.mixin.*

@TestFor(DataLocationReportController)
@Mock(DataLocationReport)
class DataLocationReportControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/dataLocationReport/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.dataLocationReportInstanceList.size() == 0
        assert model.dataLocationReportInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.dataLocationReportInstance != null
    }

    void testSave() {
        controller.save()

        assert model.dataLocationReportInstance != null
        assert view == '/dataLocationReport/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/dataLocationReport/show/1'
        assert controller.flash.message != null
        assert DataLocationReport.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/dataLocationReport/list'

        populateValidParams(params)
        def dataLocationReport = new DataLocationReport(params)

        assert dataLocationReport.save() != null

        params.id = dataLocationReport.id

        def model = controller.show()

        assert model.dataLocationReportInstance == dataLocationReport
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/dataLocationReport/list'

        populateValidParams(params)
        def dataLocationReport = new DataLocationReport(params)

        assert dataLocationReport.save() != null

        params.id = dataLocationReport.id

        def model = controller.edit()

        assert model.dataLocationReportInstance == dataLocationReport
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/dataLocationReport/list'

        response.reset()

        populateValidParams(params)
        def dataLocationReport = new DataLocationReport(params)

        assert dataLocationReport.save() != null

        // test invalid parameters in update
        params.id = dataLocationReport.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/dataLocationReport/edit"
        assert model.dataLocationReportInstance != null

        dataLocationReport.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/dataLocationReport/show/$dataLocationReport.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        dataLocationReport.clearErrors()

        populateValidParams(params)
        params.id = dataLocationReport.id
        params.version = -1
        controller.update()

        assert view == "/dataLocationReport/edit"
        assert model.dataLocationReportInstance != null
        assert model.dataLocationReportInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/dataLocationReport/list'

        response.reset()

        populateValidParams(params)
        def dataLocationReport = new DataLocationReport(params)

        assert dataLocationReport.save() != null
        assert DataLocationReport.count() == 1

        params.id = dataLocationReport.id

        controller.delete()

        assert DataLocationReport.count() == 0
        assert DataLocationReport.get(dataLocationReport.id) == null
        assert response.redirectedUrl == '/dataLocationReport/list'
    }
}
