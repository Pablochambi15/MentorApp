document.addEventListener('DOMContentLoaded', function () {
    // Validación del formulario de registro
    const registerForm = document.querySelector('form[th\\:action*="register"]');
    
    if (registerForm) {
        registerForm.addEventListener('submit', function (e) {
            const email = this.elements['email'].value;
            const password = this.elements['password'].value;
            const name = this.elements['name'].value;
            const roleSelected = this.querySelector('input[name="role"]:checked');

            // Limpiar mensajes anteriores
            document.querySelectorAll('.error-message').forEach(el => {
                el.textContent = '';
                el.style.display = 'none';
            });
            document.querySelectorAll('.input-error').forEach(el => {
                el.classList.remove('input-error');
            });

            let isValid = true;

            // Validación de email
            if (!email) {
                showError('email-error', 'El email es requerido');
                isValid = false;
            } else if (!isValidEmail(email)) {
                showError('email-error', 'Ingresa un email válido');
                isValid = false;
            }

            // Validación de contraseña
            if (!password) {
                showError('password-error', 'La contraseña es requerida');
                isValid = false;
            } else if (password.length < 6) {
                showError('password-error', 'Mínimo 6 caracteres');
                isValid = false;
            }

            // Validación de nombre
            if (!name) {
                showError('name-error', 'El nombre es obligatorio');
                isValid = false;
            }

            // Validación de rol
            if (!roleSelected) {
                showError('role-error', 'Selecciona un rol');
                isValid = false;
            }

            if (!isValid) {
                e.preventDefault();
            } else {
                // Deshabilitar botón durante envío
                const submitBtn = this.querySelector('button[type="submit"]');
                if (submitBtn) {
                    submitBtn.disabled = true;
                    submitBtn.innerHTML = 'Creando cuenta... <span class="spinner"></span>';
                }
            }
        });
    }

    // Efectos hover (opcional)
    const authLinks = document.querySelectorAll('.auth-link');
    authLinks.forEach(link => {
        link.addEventListener('mouseenter', () => {
            link.style.backgroundColor = '#f0f4ff';
        });
        link.addEventListener('mouseleave', () => {
            link.style.backgroundColor = '';
        });
    });
});

// Funciones auxiliares
function isValidEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

function showError(id, message) {
    const errorElement = document.getElementById(id);
    if (!errorElement) return;
    
    errorElement.textContent = message;
    errorElement.style.display = 'block';
    
    // Busca el input asociado al error
    let inputElement;
    if (id === 'role-error') {
        inputElement = document.querySelector('.role-options');
    } else {
        inputElement = errorElement.previousElementSibling;
    }
    
    if (inputElement) {
        inputElement.classList.add('input-error');
    }
}