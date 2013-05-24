package org.chai.memms.report

import org.springframework.dao.DataIntegrityViolationException

class CategoryTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
/**
    def index() {
        redirect(action: "list", params: params)
    }*/
	
def scaffold = true
    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [categoryTypeInstanceList: IndicatorCategory.list(params), categoryTypeInstanceTotal: IndicatorCategory.count()]
    }

    def create() {
        [categoryTypeInstance: new IndicatorCategory(params)]
    }

    def save() {
        def categoryTypeInstance = new IndicatorCategory(params)
        if (!categoryTypeInstance.save(flush: true)) {
            render(view: "create", model: [categoryTypeInstance: categoryTypeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'categoryType.label', default: 'CategoryType'), categoryTypeInstance.id])
        redirect(action: "list", id: categoryTypeInstance.id)
    }

    def show(Long id) {
        def categoryTypeInstance = IndicatorCategory.get(id)
        if (!categoryTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'categoryType.label', default: 'CategoryType'), id])
            redirect(action: "list")
            return
        }

        [categoryTypeInstance: categoryTypeInstance]
    }

    def edit(Long id) {
        def categoryTypeInstance = IndicatorCategory.get(id)
        if (!categoryTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'categoryType.label', default: 'CategoryType'), id])
            redirect(action: "list")
            return
        }

        [categoryTypeInstance: categoryTypeInstance]
    }

    def update(Long id, Long version) {
        def categoryTypeInstance = IndicatorCategory.get(id)
        if (!categoryTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'categoryType.label', default: 'CategoryType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (categoryTypeInstance.version > version) {
                categoryTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'categoryType.label', default: 'CategoryType')] as Object[],
                          "Another user has updated this CategoryType while you were editing")
                render(view: "edit", model: [categoryTypeInstance: categoryTypeInstance])
                return
            }
        }

        categoryTypeInstance.properties = params

        if (!categoryTypeInstance.save(flush: true)) {
            render(view: "edit", model: [categoryTypeInstance: categoryTypeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'categoryType.label', default: 'CategoryType'), categoryTypeInstance.id])
        redirect(action: "list", id: categoryTypeInstance.id)
    }

    def delete(Long id) {
        def categoryTypeInstance = IndicatorCategory.get(id)
        if (!categoryTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'categoryType.label', default: 'CategoryType'), id])
            redirect(action: "list")
            return
        }

        try {
            categoryTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'categoryType.label', default: 'CategoryType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'categoryType.label', default: 'CategoryType'), id])
            redirect(action: "show", id: id)
        }
    }
}
