tinymce.init({
    selector: ".tobago-textarea-markup-wysiwyg",
    content_security_policy: "default-src 'self'",
    language: "de",
    plugins: "print table insertdatetime link autolink charmap codesample textcolor paste advlist lists",
    /*'advlist lists image preview anchor',
    'searchreplace visualblocks code fullscreen',
    ' media paste code help wordcount'*/
    insertdatetime_formats: ["%H:%M", "%H:%M:%S", "%d.%m.%Y", "%d.%m.%Y %H:%M"],
    toolbar: "print | styleselect bold italic underline | alignleft aligncenter alignright forecolor backcolor | bullist numlist | link codesample",
    removed_menuitems: "newdocument file",
    branding: false,
    color_map: [
        "000000", "Schwarz",
        "FFFFFF", "Weiß",
        "DEDFA2", "Pistazie",
        "FEEBD7", "Puder",
        "EFDBAC", "Creme",
        "D2E9E5", "Eisblau",
        "B7C5D5", "Himmelblau",
        "C7C7C7", "Silbergrau",
        "182257", "NORD/LB Blau",
        "00979E", "Petrol",
        "28B9DA", "Mittelblau",
        "788C94", "Grau",
        "009C4B", "Grün",
        "D30040", "Himbeer"
    ],
    setup: function (editor) {
        editor.on('init', function (event) {
            var $textarea = jQuery("#" + event.target.id.replace(/:/g, "\\:"));
            var $editor = $textarea.siblings(".tox-tinymce");

            $editor.css("flex-grow", $textarea.css("flex-grow"));
            $editor.css("flex-shrink", $textarea.css("flex-shrink"));
            $editor.css("flex-basis", $textarea.css("flex-basis"));

            if ($textarea.attr("disabled") === "disabled") {
                tinymce.get(event.target.id).setMode("readonly");
            }
        });
    }
});
