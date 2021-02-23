function addItem(typeName) {
    const ul = document.getElementById(typeName)
    const itemId = uuidv4()
    const li = document.createElement("li")
    li.setAttribute("id", itemId)
    const input = document.createElement('input')
    input.setAttribute("type", "text")
    input.setAttribute("name", typeName)
    input.setAttribute("size", "60")
    input.setAttribute("required", "")
    const button = createButton("Удалить", "deleteItem", itemId)
    button.setAttribute("style", "margin: 2px")
    li.appendChild(input)
    li.appendChild(button)
    ul.appendChild(li)
}

function addPosition(orgId) {
    const table = document.getElementById(orgId + ".posTable")
    const posId = uuidv4()
    const idInput = document.createElement("input")
    idInput.setAttribute("type", "hidden")
    idInput.setAttribute("name", orgId + ".position")
    idInput.setAttribute("value", posId)
    const posIdItem = uuidv4()
    const tr = document.createElement("tr")
    tr.setAttribute("id", posIdItem)
    const td = document.createElement("td")
    td.setAttribute("colspan", "2")
    const position = document.createElement("b")
    position.appendChild(document.createTextNode("Позиция:"))
    const positionDl = createDl(position, posId + ".title", "text", true)
    const startDateDl = createDl(document.createTextNode("Начало:"), posId + ".startDate", "date", true)
    const endDateDl = createDl(document.createTextNode("Окончание:"), posId + ".endDate", "date")
    const descriptionDl = createDl(document.createTextNode("Описание:"), posId + ".description")
    const button = createButton("Удалить", "deletePosition", posIdItem)
    td.appendChild(positionDl)
    td.appendChild(startDateDl)
    td.appendChild(endDateDl)
    td.appendChild(descriptionDl)
    td.appendChild(button)
    tr.appendChild(idInput)
    tr.appendChild(td)
    table.appendChild(tr)
}

function addOrganisation(typeName) {
    const orgId = uuidv4()
    const section = document.getElementById(typeName)
    const orgSection = document.createElement("section")
    orgSection.setAttribute("id", orgId)
    const nameB = document.createElement("b")
    nameB.appendChild(document.createTextNode("Название:"))
    const nameDl = createDl(nameB, orgId + ".name", "text", true)
    const idInput = document.createElement("input")
    idInput.setAttribute("type", "hidden")
    idInput.setAttribute("name", typeName)
    idInput.setAttribute("value", orgId)
    const urlDl = createDl(document.createTextNode("Ссылка:"), orgId + ".url", "text")
    const table = document.createElement("table")
    table.setAttribute("id", orgId + ".posTable")
    table.setAttribute("cellpadding", "10")
    const addPosButton = createButton("Добавить позицию",  "addPosition", orgId)
    const deleteButton = createButton("Удалить организацию",  "deleteOrganisation", orgId)
    deleteButton.setAttribute("style", "margin-left: 5px")
    orgSection.appendChild(idInput)
    orgSection.appendChild(nameDl)
    orgSection.appendChild(urlDl)
    orgSection.appendChild(addPosButton)
    orgSection.appendChild(deleteButton)
    orgSection.appendChild(table)
    orgSection.appendChild(document.createElement("hr"))
    section.appendChild(orgSection)
}

function deleteItem(itemId) {
    const li = document.getElementById(itemId)
    li.parentElement.removeChild(li)
}

function deletePosition(posTrId) {
    const tr = document.getElementById(posTrId)
    tr.parentElement.removeChild(tr)
}

function deleteOrganisation(orgId) {
    const section = document.getElementById(orgId)
    section.parentElement.removeChild(section)
}

function createDl(text, name, type, required) {
    const dl = document.createElement("dl")
    const dt = document.createElement("dt")
    dt.appendChild(text)
    const dd = document.createElement("dd")
    const input = document.createElement("input")
    input.setAttribute("type", type)
    input.setAttribute("name", name)
    input.setAttribute("size", "60")
    if (required) {
        input.setAttribute("required", "")
    }
    dd.appendChild(input)
    dl.appendChild(dt)
    dl.appendChild(dd)
    return dl
}

function createButton(text, funcName, args) {
    const button = document.createElement("button")
    button.setAttribute("type", "button")
    button.setAttribute("onclick", funcName +"('" + args + "')")
    button.appendChild(document.createTextNode(text))
    return button
}

function uuidv4() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        const r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}