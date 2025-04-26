// Main JavaScript File for Lars Design Website
document.addEventListener('DOMContentLoaded', function() {
    // Preloader
    const preloader = document.querySelector('.preloader');
    const preloaderProgress = document.querySelector('.preloader-progress-bar');
    
    // Simulate loading progress
    let progress = 0;
    const loadingInterval = setInterval(() => {
        progress += Math.random() * 10;
        if (progress >= 100) {
            progress = 100;
            clearInterval(loadingInterval);
            setTimeout(() => {
                preloader.style.opacity = '0';
                preloader.style.visibility = 'hidden';
                document.body.classList.remove('no-scroll');
            }, 500);
        }
        preloaderProgress.style.width = `${progress}%`;
    }, 200);

    // Theme Toggle
    const themeToggle = document.getElementById('themeToggle');
    const html = document.documentElement;
    const logoLight = document.querySelector('.logo-light');
    const logoDark = document.querySelector('.logo-dark');
    
    // Check for saved theme preference or use preferred color scheme
    const savedTheme = localStorage.getItem('theme') || 
                      (window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light');
    html.setAttribute('data-theme', savedTheme);
    updateThemeIcon(savedTheme);
    
    themeToggle.addEventListener('click', () => {
        const currentTheme = html.getAttribute('data-theme');
        const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
        html.setAttribute('data-theme', newTheme);
        localStorage.setItem('theme', newTheme);
        updateThemeIcon(newTheme);
    });
    
    function updateThemeIcon(theme) {
        const icon = themeToggle.querySelector('i');
        icon.className = theme === 'dark' ? 'fas fa-sun' : 'fas fa-moon';
        
        // Toggle logo visibility based on theme
        if (theme === 'dark') {
            logoLight.style.display = 'block';
            logoDark.style.display = 'none';
        } else {
            logoLight.style.display = 'none';
            logoDark.style.display = 'block';
        }
    }

    // Mobile Navigation
    const mobileNavToggle = document.querySelector('.mobile-nav-toggle');
    const navLinks = document.querySelector('.nav-links');
    const overlay = document.querySelector('.overlay');
    
    mobileNavToggle.addEventListener('click', () => {
        const isOpen = navLinks.classList.toggle('active');
        mobileNavToggle.classList.toggle('active');
        overlay.classList.toggle('active');
        document.body.classList.toggle('no-scroll', isOpen);
        
        // Animate nav links when opening
        if (isOpen) {
            const links = navLinks.querySelectorAll('a');
            links.forEach((link, index) => {
                link.style.transitionDelay = `${index * 0.1}s`;
            });
        }
    });
    
    // Close mobile menu when clicking on overlay or a nav link
    overlay.addEventListener('click', closeMobileMenu);
    document.querySelectorAll('.nav-links a').forEach(link => {
        link.addEventListener('click', closeMobileMenu);
    });
    
    function closeMobileMenu() {
        navLinks.classList.remove('active');
        mobileNavToggle.classList.remove('active');
        overlay.classList.remove('active');
        document.body.classList.remove('no-scroll');
    }

    // Smooth scrolling for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            
            const targetId = this.getAttribute('href');
            if (targetId === '#') return;
            
            const targetElement = document.querySelector(targetId);
            if (targetElement) {
                window.scrollTo({
                    top: targetElement.offsetTop - 80,
                    behavior: 'smooth'
                });
            }
        });
    });

    // Typing Animation in Hero Section
    const typingElement = document.querySelector('.typing-animation');
    if (typingElement) {
        const phrases = ["Yaratıcı Tasarımlar", "Etkileyici Deneyimler", "Özgün Çözümler"];
        let currentPhrase = 0;
        let charIndex = 0;
        let isDeleting = false;
        let isEnd = false;
        
        function type() {
            const currentText = phrases[currentPhrase];
            
            if (isDeleting) {
                typingElement.textContent = currentText.substring(0, charIndex - 1);
                charIndex--;
            } else {
                typingElement.textContent = currentText.substring(0, charIndex + 1);
                charIndex++;
            }
            
            if (!isDeleting && charIndex === currentText.length) {
                isEnd = true;
                setTimeout(() => {
                    isDeleting = true;
                    type();
                }, 1500);
            } else if (isDeleting && charIndex === 0) {
                isDeleting = false;
                currentPhrase = (currentPhrase + 1) % phrases.length;
                setTimeout(type, 500);
            } else {
                const speed = isDeleting ? 50 : 100;
                setTimeout(type, speed);
            }
        }
        
        setTimeout(type, 1000);
    }

    // Animate skills on scroll
    const skillItems = document.querySelectorAll('.skill-item');
    if (skillItems.length > 0) {
        const animateSkills = () => {
            skillItems.forEach(item => {
                const percent = item.getAttribute('data-percent');
                const progressBar = item.querySelector('.skill-progress');
                progressBar.style.width = `${percent}%`;
            });
        };
        
        // Use IntersectionObserver to trigger animation when skills section is in view
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    animateSkills();
                    observer.unobserve(entry.target);
                }
            });
        }, { threshold: 0.2 });
        
        observer.observe(document.querySelector('.skills'));
    }

    // Services Data and Rendering
    const servicesGrid = document.querySelector('.services-grid');
    if (servicesGrid) {
        const services = [
            {
                icon: 'fas fa-laptop-code',
                title: 'Web Tasarım',
                description: 'Modern, responsive ve kullanıcı dostu web tasarımları ile markanızı öne çıkarıyoruz.'
            },
            {
                icon: 'fas fa-mobile-alt',
                title: 'UI/UX Tasarım',
                description: 'Kullanıcı deneyimini ön planda tutarak etkileyici arayüzler tasarlıyoruz.'
            },
            {
                icon: 'fas fa-paint-brush',
                title: 'Grafik Tasarım',
                description: 'Marka kimliğinize uygun logo, broşür ve diğer grafik tasarımlar.'
            },
            {
                icon: 'fas fa-bullhorn',
                title: 'Dijital Pazarlama',
                description: 'Hedef kitlenize ulaşmak için etkili dijital pazarlama stratejileri.'
            },
            {
                icon: 'fas fa-shopping-cart',
                title: 'E-Ticaret',
                description: 'Satışlarınızı artıracak profesyonel e-ticaret çözümleri.'
            },
            {
                icon: 'fas fa-chart-line',
                title: 'SEO Optimizasyon',
                description: 'Arama motorlarında üst sıralarda çıkmanız için SEO çalışmaları.'
            }
        ];
        
        services.forEach(service => {
            const serviceCard = document.createElement('div');
            serviceCard.className = 'service-card';
            serviceCard.innerHTML = `
                <div class="service-icon">
                    <i class="${service.icon}"></i>
                </div>
                <h3 class="service-title">${service.title}</h3>
                <p class="service-description">${service.description}</p>
            `;
            servicesGrid.appendChild(serviceCard);
        });
    }

    // Portfolio Filtering
    const filterButtons = document.querySelectorAll('.filter-btn');
    const portfolioItems = document.querySelectorAll('.portfolio-item');
    const portfolioGrid = document.querySelector('.portfolio-grid');
    
    if (filterButtons.length > 0 && portfolioItems.length > 0) {
        filterButtons.forEach(button => {
            button.addEventListener('click', () => {
                // Update active state of buttons
                filterButtons.forEach(btn => btn.classList.remove('active'));
                button.classList.add('active');
                
                const filter = button.getAttribute('data-filter');
                
                // Filter portfolio items
                portfolioItems.forEach(item => {
                    if (filter === 'all' || item.getAttribute('data-category').includes(filter)) {
                        item.style.display = 'block';
                    } else {
                        item.style.display = 'none';
                    }
                });
                
                // Re-layout the grid
                setTimeout(() => {
                    if (typeof Masonry !== 'undefined') {
                        new Masonry(portfolioGrid, {
                            itemSelector: '.portfolio-item',
                            columnWidth: '.portfolio-item',
                            percentPosition: true,
                            transitionDuration: '0.4s'
                        });
                    }
                }, 300);
            });
        });
    }

    // Portfolio Modal
    const portfolioModal = document.getElementById('projectModal');
    const portfolioModalContent = portfolioModal.querySelector('.project-modal-content');
    
    document.querySelectorAll('.portfolio-item').forEach(item => {
        item.addEventListener('click', () => {
            // In a real implementation, you would fetch actual project data here
            // For demo purposes, we'll use placeholder data
            const projectData = {
                title: "Örnek Proje",
                category: "Web Tasarım",
                description: "Bu proje, müşterimiz için geliştirdiğimiz modern ve responsive bir web sitesidir. Kullanıcı deneyimini ön planda tutarak tasarlanmıştır ve yüksek dönüşüm oranları sağlamaktadır.",
                client: "ABC Şirketi",
                date: "15 Haziran 2023",
                categories: "Web Tasarım, UI/UX",
                link: "#"
            };
            
            // Populate modal with project data
            portfolioModal.querySelector('.project-title').textContent = projectData.title;
            portfolioModal.querySelector('.project-category').textContent = projectData.category;
            portfolioModal.querySelector('.project-description').textContent = projectData.description;
            portfolioModal.querySelector('.project-client').textContent = projectData.client;
            portfolioModal.querySelector('.project-date').textContent = projectData.date;
            portfolioModal.querySelector('.project-categories').textContent = projectData.categories;
            portfolioModal.querySelector('.project-link').setAttribute('href', projectData.link);
            
            // Set main image (in a real implementation, you would have multiple images)
            const mainImage = portfolioModal.querySelector('.project-main-image img');
            mainImage.src = "assets/images/portfolio-sample.jpg";
            mainImage.alt = projectData.title;
            
            // Clear thumbnails and add new ones (demo with 3 images)
            const thumbnailsContainer = portfolioModal.querySelector('.project-thumbnails');
            thumbnailsContainer.innerHTML = '';
            
            for (let i = 1; i <= 3; i++) {
                const thumbnail = document.createElement('img');
                thumbnail.src = `assets/images/portfolio-sample-${i}.jpg`;
                thumbnail.alt = `${projectData.title} - ${i}`;
                thumbnail.addEventListener('click', () => {
                    mainImage.src = thumbnail.src;
                    mainImage.alt = thumbnail.alt;
                    
                    // Update active thumbnail
                    thumbnailsContainer.querySelectorAll('img').forEach(img => {
                        img.classList.remove('active');
                    });
                    thumbnail.classList.add('active');
                });
                
                if (i === 1) thumbnail.classList.add('active');
                thumbnailsContainer.appendChild(thumbnail);
            }
            
            // Show modal
            openModal(portfolioModal);
        });
    });

    // Testimonials Slider
    const testimonialsSlider = document.querySelector('.testimonials-slider');
    if (testimonialsSlider) {
        new Splide(testimonialsSlider, {
            type: 'loop',
            perPage: 1,
            perMove: 1,
            gap: '2rem',
            pagination: false,
            arrows: false,
            autoplay: true,
            interval: 5000,
            breakpoints: {
                768: {
                    gap: '1rem'
                }
            }
        }).mount();
    }

    // Clients Logo Marquee Animation
    const clientTrack = document.querySelector('.client-track');
    if (clientTrack) {
        // Duplicate client logos for seamless looping
        const logos = clientTrack.innerHTML;
        clientTrack.innerHTML = logos + logos;
        
        // Pause animation on hover
        clientTrack.addEventListener('mouseenter', () => {
            clientTrack.style.animationPlayState = 'paused';
        });
        
        clientTrack.addEventListener('mouseleave', () => {
            clientTrack.style.animationPlayState = 'running';
        });
    }

    // Stats Counter Animation
    const statNumbers = document.querySelectorAll('.stat-number');
    if (statNumbers.length > 0) {
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    statNumbers.forEach(stat => {
                        const target = parseInt(stat.getAttribute('data-count'));
                        const duration = 2000;
                        const start = 0;
                        const increment = target / (duration / 16);
                        
                        let current = start;
                        const timer = setInterval(() => {
                            current += increment;
                            stat.textContent = Math.floor(current);
                            
                            if (current >= target) {
                                stat.textContent = target;
                                clearInterval(timer);
                            }
                        }, 16);
                    });
                    
                    observer.unobserve(entry.target);
                }
            });
        }, { threshold: 0.5 });
        
        observer.observe(document.querySelector('.stats-section'));
    }

    // Contact Form Validation
    const contactForm = document.getElementById('contactForm');
    if (contactForm) {
        contactForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Reset error messages
            document.querySelectorAll('.error-message').forEach(el => {
                el.textContent = '';
                el.style.display = 'none';
            });
            
            // Get form values
            const name = document.getElementById('name').value.trim();
            const email = document.getElementById('email').value.trim();
            const subject = document.getElementById('subject').value.trim();
            const message = document.getElementById('message').value.trim();
            
            let isValid = true;
            
            // Validate name
            if (!name) {
                document.getElementById('nameError').textContent = 'Lütfen adınızı giriniz';
                document.getElementById('nameError').style.display = 'block';
                isValid = false;
            }
            
            // Validate email
            if (!email) {
                document.getElementById('emailError').textContent = 'Lütfen email adresinizi giriniz';
                document.getElementById('emailError').style.display = 'block';
                isValid = false;
            } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
                document.getElementById('emailError').textContent = 'Lütfen geçerli bir email adresi giriniz';
                document.getElementById('emailError').style.display = 'block';
                isValid = false;
            }
            
            // Validate subject
            if (!subject) {
                document.getElementById('subjectError').textContent = 'Lütfen konu giriniz';
                document.getElementById('subjectError').style.display = 'block';
                isValid = false;
            }
            
            // Validate message
            if (!message) {
                document.getElementById('messageError').textContent = 'Lütfen mesajınızı giriniz';
                document.getElementById('messageError').style.display = 'block';
                isValid = false;
            } else if (message.length < 20) {
                document.getElementById('messageError').textContent = 'Mesajınız en az 20 karakter olmalıdır';
                document.getElementById('messageError').style.display = 'block';
                isValid = false;
            }
            
            // If form is valid, submit it (in a real implementation, you would use AJAX)
            if (isValid) {
                const feedback = document.querySelector('.form-feedback');
                feedback.textContent = 'Mesajınız başarıyla gönderildi! En kısa sürede sizinle iletişime geçeceğiz.';
                feedback.classList.add('success');
                feedback.style.display = 'block';
                
                // Reset form
                contactForm.reset();
                
                // Hide feedback after 5 seconds
                setTimeout(() => {
                    feedback.style.display = 'none';
                }, 5000);
            }
        });
    }

    // Privacy Modal
    const privacyModal = document.getElementById('privacyModal');
    const privacyLink = document.getElementById('privacyLink');
    
    if (privacyLink) {
        privacyLink.addEventListener('click', (e) => {
            e.preventDefault();
            
            // In a real implementation, you would load the privacy policy content here
            const privacyContent = `
                <h3>Gizlilik Politikası</h3>
                <p>Lars Design olarak, ziyaretçilerimizin gizliliğine büyük önem veriyoruz. Bu gizlilik politikası, kişisel bilgilerinizin nasıl toplandığını, kullanıldığını ve korunduğunu açıklamaktadır.</p>
                
                <h4>Toplanan Bilgiler</h4>
                <p>Web sitemizi ziyaret ettiğinizde, IP adresiniz, tarayıcı türünüz, işletim sisteminiz ve ziyaret saatleriniz gibi bazı teknik bilgiler otomatik olarak kaydedilebilir.</p>
                
                <h4>Çerezler (Cookies)</h4>
                <p>Sitemiz, kullanıcı deneyimini iyileştirmek için çerezler kullanmaktadır. Çerezler, web sitenin düzgün çalışmasını sağlamak ve kullanım istatistikleri toplamak için kullanılır.</p>
                
                <h4>Bilgilerin Kullanımı</h4>
                <p>Toplanan bilgiler, web sitemizi iyileştirmek, kullanıcı deneyimini geliştirmek ve yasal yükümlülüklerimizi yerine getirmek için kullanılır.</p>
                
                <h4>Bilgilerin Paylaşılması</h4>
                <p>Kişisel bilgileriniz, yasal bir zorunluluk olmadıkça üçüncü taraflarla paylaşılmaz.</p>
                
                <h4>Güvenlik</h4>
                <p>Kişisel bilgilerinizin güvenliği için uygun teknik ve organizasyonel önlemleri alıyoruz.</p>
                
                <p>Bu politika 1 Ocak 2023 tarihinden itibaren geçerlidir ve güncellenebilir. Güncellemelerden haberdar olmak için bu sayfayı periyodik olarak kontrol etmenizi öneririz.</p>
            `;
            
            privacyModal.querySelector('.modal-body').innerHTML = privacyContent;
            openModal(privacyModal);
        });
    }

    // Modal Functions
    function openModal(modal) {
        modal.style.display = 'block';
        document.body.classList.add('no-scroll');
        
        // Close modal when clicking on close button or outside content
        modal.querySelector('.close-modal').addEventListener('click', () => closeModal(modal));
        modal.addEventListener('click', (e) => {
            if (e.target === modal) {
                closeModal(modal);
            }
        });
    }
    
    function closeModal(modal) {
        modal.style.display = 'none';
        document.body.classList.remove('no-scroll');
    }

    // Scroll to Top Button
    const scrollTopBtn = document.getElementById('scrollTop');
    
    window.addEventListener('scroll', () => {
        if (window.pageYOffset > 300) {
            scrollTopBtn.classList.add('active');
        } else {
            scrollTopBtn.classList.remove('active');
        }
    });
    
    scrollTopBtn.addEventListener('click', () => {
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    });

    // Current Year in Footer
    document.getElementById('currentYear').textContent = new Date().getFullYear();

    // Custom Cursor
    const cursorFollower = document.querySelector('.cursor-follower');
    const cursorDot = document.querySelector('.cursor-dot');
    
    if (cursorFollower && cursorDot) {
        document.addEventListener('mousemove', (e) => {
            cursorDot.style.left = `${e.clientX}px`;
            cursorDot.style.top = `${e.clientY}px`;
            
            // Delayed movement for the follower
            setTimeout(() => {
                cursorFollower.style.left = `${e.clientX}px`;
                cursorFollower.style.top = `${e.clientY}px`;
            }, 100);
        });
        
        // Cursor effects on interactive elements
        const interactiveElements = document.querySelectorAll('a, button, .portfolio-item, .service-card, input, textarea');
        
        interactiveElements.forEach(el => {
            el.addEventListener('mouseenter', () => {
                cursorFollower.style.transform = 'translate(-50%, -50%) scale(1.5)';
                cursorFollower.style.backgroundColor = 'rgba(255, 51, 102, 0.4)';
                cursorDot.style.transform = 'translate(-50%, -50%) scale(0.5)';
            });
            
            el.addEventListener('mouseleave', () => {
                cursorFollower.style.transform = 'translate(-50%, -50%) scale(1)';
                cursorFollower.style.backgroundColor = 'rgba(255, 51, 102, 0.2)';
                cursorDot.style.transform = 'translate(-50%, -50%) scale(1)';
            });
        });
    }

    // Navigation Loading Bar
    const loadingBar = document.querySelector('.loading-bar');
    window.addEventListener('scroll', () => {
        const scrollHeight = document.documentElement.scrollHeight - window.innerHeight;
        const scrollPosition = window.scrollY;
        const scrollPercentage = (scrollPosition / scrollHeight) * 100;
        loadingBar.style.width = `${scrollPercentage}%`;
    });

    // Initialize AOS (Animate On Scroll)
    AOS.init({
        duration: 800,
        easing: 'ease-in-out',
        once: true,
        offset: 100
    });

    // Initialize Particles.js for background
    if (typeof particlesJS !== 'undefined') {
        particlesJS('particles-js', {
            particles: {
                number: {
                    value: 80,
                    density: {
                        enable: true,
                        value_area: 800
                    }
                },
                color: {
                    value: "#ff3366"
                },
                shape: {
                    type: "circle",
                    stroke: {
                        width: 0,
                        color: "#000000"
                    },
                    polygon: {
                        nb_sides: 5
                    }
                },
                opacity: {
                    value: 0.5,
                    random: false,
                    anim: {
                        enable: false,
                        speed: 1,
                        opacity_min: 0.1,
                        sync: false
                    }
                },
                size: {
                    value: 3,
                    random: true,
                    anim: {
                        enable: false,
                        speed: 40,
                        size_min: 0.1,
                        sync: false
                    }
                },
                line_linked: {
                    enable: true,
                    distance: 150,
                    color: "#4834d4",
                    opacity: 0.4,
                    width: 1
                },
                move: {
                    enable: true,
                    speed: 2,
                    direction: "none",
                    random: false,
                    straight: false,
                    out_mode: "out",
                    bounce: false,
                    attract: {
                        enable: false,
                        rotateX: 600,
                        rotateY: 1200
                    }
                }
            },
            interactivity: {
                detect_on: "canvas",
                events: {
                    onhover: {
                        enable: true,
                        mode: "grab"
                    },
                    onclick: {
                        enable: true,
                        mode: "push"
                    },
                    resize: true
                },
                modes: {
                    grab: {
                        distance: 140,
                        line_linked: {
                            opacity: 1
                        }
                    },
                    bubble: {
                        distance: 400,
                        size: 40,
                        duration: 2,
                        opacity: 8,
                        speed: 3
                    },
                    repulse: {
                        distance: 200,
                        duration: 0.4
                    },
                    push: {
                        particles_nb: 4
                    },
                    remove: {
                        particles_nb: 2
                    }
                }
            },
            retina_detect: true
        });
    }
});

// Load additional scripts after DOM is loaded
window.addEventListener('load', function() {
    // Lazy loading for images
    const lazyImages = document.querySelectorAll('img[loading="lazy"]');
    
    if ('IntersectionObserver' in window) {
        const imageObserver = new IntersectionObserver((entries, observer) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    const img = entry.target;
                    img.src = img.dataset.src || img.src;
                    img.removeAttribute('loading');
                    observer.unobserve(img);
                }
            });
        }, { rootMargin: '200px' });
        
        lazyImages.forEach(img => imageObserver.observe(img));
    }
});