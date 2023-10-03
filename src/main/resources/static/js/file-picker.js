
(async () => {
    console.log(`inside of file-picker.js`);
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');

// FILE PICKER START -----------------------------------------------------------------
    /** File picker API*/
    const getFilePicker = async () => {
        const client = await filestack.init(FILE_STACK_API);
        const options = {
            fromSources: ["local_file_system"],
            transformations: {
                crop: { aspectRatio: 1 / 1, force: true},
                circle: true,
            },
            imageMin: [400, 400],
            imageMax: [400, 400],
            accept: ["image/jpeg", "image/png", "image/bmp"],
            onFileUploadFinished: async file => {
                console.log(file.url);

                // Bringing defaultPicBtn back -------
                const defaultPicBtn = document.querySelector("#default-picBtn");
                defaultPicBtn.style.display = 'inline';
                // -----------------------------------

                const imageElement = document.querySelector("#profile-pic-img");
                imageElement.src = file.url;

                let fileStackPic = await fetch(`/profile/profile-pic`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-CSRF-TOKEN': csrfToken
                    },
                    body: JSON.stringify({profilePic: file.url})
                });
                console.log(await fileStackPic.json());
            }
        };
        client.picker(options).open();
    };

    /** Event Listener on Button */
    const fileStackBtn = document.querySelector("#file-stack-picBtn");
    fileStackBtn.addEventListener("click", getFilePicker);
// FILE PICKER END -----------------------------------------------------------------

// DEFAULT PIC START -----------------------------------------------------------------
    const getDefaultPic = async () => {
        const imageElement = document.querySelector("#profile-pic-img");
        imageElement.src = "/img/user-circle-icon-png-modified.png";

            let defaultPic = await fetch(`/profile/default-pic`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': csrfToken
                },
                body: JSON.stringify({profilePic: "/img/user-circle-icon-png-modified.png"})
            });
            console.log(await defaultPic.json());

    };

    /** Event Listener on default-picBtn*/
    const defaultPicBtn = document.querySelector("#default-picBtn");
    defaultPicBtn.addEventListener("click", getDefaultPic);

    // ------------------------------------------------------------------------------------
    // Part 2
    function updateDOMAfterProfileDefault() {
        const imageElement = document.querySelector("#profile-pic-img");
        imageElement.src = "/img/user-circle-icon-png-modified.png";
        defaultPicBtn.style.display = 'none';
    }

    defaultPicBtn.addEventListener('click', () => {
        updateDOMAfterProfileDefault();
    });
    // ------------------------------------------------------------------------------------

// DEFAULT PIC END -----------------------------------------------------------------


}) ();