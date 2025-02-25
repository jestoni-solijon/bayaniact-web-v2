document.getElementById("formSelect").addEventListener("change", function () {
        const selectedOption = this.options[this.selectedIndex];
        const description = selectedOption.getAttribute("data-description") || "";
        const price = selectedOption.getAttribute("data-price") || "";

        // Populate the fields
        document.getElementById("formDescription").value = description;
        document.getElementById("formPrice").value = price;
    });