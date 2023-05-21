function animateValue(element, start, end, duration) {
    let current = start;
    const increment = Math.floor((end - start) / duration);
    const obj = document.getElementById(element);
  
    const timer = setInterval(() => {
      current += increment;
      obj.innerHTML = current;
      if (current >= end) {
        clearInterval(timer);
        obj.innerHTML = end;
      }
    }, 1);
  }

  animateValue("counter", 0, 100, 2000);