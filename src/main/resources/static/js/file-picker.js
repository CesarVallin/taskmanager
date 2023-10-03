
(async () => {
    console.log(`inside of file-picker.js`);

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
            onFileUploadFinished: file => {
                console.log(file.url);
                const imageElement = document.querySelector("#profile-pic-img");
                imageElement.src = file.url;

                let fileStackPic = await fetch(`/profile/profile-pic`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-CSRF-TOKEN': csrfToken
                    },
                    body: ''
                });
            }
        };
        client.picker(options).open();
    };



}) ();